import Foundation
import AEPCore
import AEPAnalytics
import AEPUserProfile
import AEPIdentity
import AEPLifecycle
import AEPSignal
import AEPServices

@objc public class AepAnalytics: NSObject {
    @objc public func load(appId: String) {
        if (UserDefaults.standard.bool(forKey: "HasLaunchedOnce")) {
            // App already launched
            print("[AEP Plugin] App already launched")
        } else {
            // This is the first launch ever
            print("[AEP Plugin] This is the first launch ever")
            // save flag
            UserDefaults.standard.set(true, forKey: "HasLaunchedOnce")
            UserDefaults.standard.synchronize()

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
}
