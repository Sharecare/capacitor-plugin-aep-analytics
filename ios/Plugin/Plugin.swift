import Foundation
import Capacitor
import AEPCore
import AEPAnalytics
import AEPUserProfile
import AEPIdentity
import AEPLifecycle
import AEPSignal
import AEPServices

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */

@objc(AepAnalytics)
public class AepAnalytics: CAPPlugin {

    @objc override public func load() {
        // Called when the plugin is first constructed in the bridge

        if (UserDefaults.standard.bool(forKey: "HasLaunchedOnce")) {
            // App already launched
            print("[AEP Plugin] App already launched")
        } else {
            // This is the first launch ever
            print("[AEP Plugin] This is the first launch ever")
            // save flag
            UserDefaults.standard.set(true, forKey: "HasLaunchedOnce")
            UserDefaults.standard.synchronize()

            let appId = "7b7e1e4c7d6a/e34e345e6f5b/launch-f5b515c96664-development"

            // register aep
            print("[AEP Plugin] Init Aep with appId \(appId)")
            MobileCore.setLogLevel(.trace)
            MobileCore.registerExtensions([Signal.self, Lifecycle.self, UserProfile.self, AEPIdentity.Identity.self, Analytics.self], {
                MobileCore.configureWith(appId: appId)
                MobileCore.lifecycleStart(additionalContextData: nil)
            })

            // track install
            print("[AEP] Tracking install")
            MobileCore.track(action: "install", data: ["platform": "ios"])
        }
    }

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.success([
            "value": value
        ])
    }

    @objc func launch(_ call: CAPPluginCall) {
        call.success([
            "value": "from ios"
        ])
    }
}
