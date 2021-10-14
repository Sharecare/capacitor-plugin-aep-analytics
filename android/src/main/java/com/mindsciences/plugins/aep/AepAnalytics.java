package com.mindsciences.plugins.aep;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.Context;
import android.os.RemoteException;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import com.adobe.marketing.mobile.AdobeCallback;
import com.adobe.marketing.mobile.Analytics;
import com.adobe.marketing.mobile.Identity;
import com.adobe.marketing.mobile.InvalidInitException;
import com.adobe.marketing.mobile.Lifecycle;
import com.adobe.marketing.mobile.LoggingMode;
import com.adobe.marketing.mobile.MobileCore;
import com.adobe.marketing.mobile.MobileServices;
import com.adobe.marketing.mobile.Signal;
import com.adobe.marketing.mobile.UserProfile;
import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;

public class AepAnalytics {

    private Context _context;

    public void load(Context context, AppCompatActivity activity, String aepAppId) {
        _context = context;

        // Called when the plugin is first constructed in the bridge
        SharedPreferences sharedPref = context.getSharedPreferences("com.aepanalytics.AepApplication", Context.MODE_PRIVATE);
        this.handleGooglePlayReferrer();
        if (sharedPref.getBoolean("firstrun", true)) {
            System.out.println("### AepAnalytics - first install, using app id:\n");
            System.out.println(aepAppId);

            MobileCore.setApplication(activity.getApplication());
            MobileCore.setLogLevel(LoggingMode.DEBUG);

            try {
                MobileServices.registerExtension();
                Analytics.registerExtension();
                UserProfile.registerExtension();
                Identity.registerExtension();
                Lifecycle.registerExtension();
                Signal.registerExtension();

                MobileCore.start(new AdobeCallback () {
                    @Override
                    public void call(Object o) {
                        MobileCore.configureWithAppID(aepAppId);
                        MobileCore.lifecycleStart(null);

                        Map<String, String> additionalContextData = new HashMap<String, String>();
                        additionalContextData.put("platform", "android");
                        MobileCore.trackAction("install", additionalContextData);
                    }
                });
            } catch (InvalidInitException e) {
                System.out.println("### AepAnalytics - Error\n");
                System.out.println(e);
            }

            sharedPref.edit().putBoolean("firstrun", false).commit();
        } else {
            System.out.println("### AepAnalytics - Not the first install\n");
        }
    }

    void handleGooglePlayReferrer() {
        // from https://experienceleague.adobe.com/docs/mobile-services/android/acquisition-android/acquisition.html?lang=en#acquisition-android

        // Google recommends only calling this API the first time you need it:
        // https://developer.android.com/google/play/installreferrer/library#install-referrer

        // Store a boolean in SharedPreferences to ensure we only call it once.
        final SharedPreferences prefs = _context.getSharedPreferences("acquisition", 0);
            if (prefs != null) {
                if (prefs.getBoolean("referrerHasBeenProcessed", false)) {
                    return;
                }
            }


        final InstallReferrerClient referrerClient = InstallReferrerClient.newBuilder(_context).build();

        referrerClient.startConnection(new InstallReferrerStateListener() {
            private boolean complete = false;

            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        // connection is established
                        complete();
                        try {
                            final ReferrerDetails details = referrerClient.getInstallReferrer();

                            // pass the install referrer url to the SDK
                            MobileServices.processGooglePlayInstallReferrerUrl(details.getInstallReferrer());

                        } catch (final RemoteException ex) {
                            System.out.println("### AepAnalytics - Error\n");
                            System.out.println("### Acquisition - RemoteException while retrieving referrer information \n");
                            System.out.println(ex.getLocalizedMessage() == null ? "unknown" : ex.getLocalizedMessage());
                        } finally {
                            referrerClient.endConnection();
                        }
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                    default:
                        // API not available in the Play Store app - nothing to do here
                        complete();
                        referrerClient.endConnection();
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
                if (!complete) {
                    // something went wrong trying to get a connection, try again
                    referrerClient.startConnection(this);
                }
            }

            void complete() {
                complete = true;
                SharedPreferences.Editor editor = _context.getSharedPreferences("acquisition", 0).edit();
                editor.putBoolean("referrerHasBeenProcessed", true);
                editor.apply();
            }
        });
    }
}
