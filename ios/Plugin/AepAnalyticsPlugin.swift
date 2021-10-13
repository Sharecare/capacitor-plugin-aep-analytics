import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(AepAnalyticsPlugin)
public class AepAnalyticsPlugin: CAPPlugin {
    private let implementation = AepAnalytics()
    
    override public func load() {
        print("[AEP Plugin] Getting AEPEnabled & AEPAppId keys from plist")
        
        let aepEnabled: Bool = Bundle.main.object(forInfoDictionaryKey: "AEPEnabled") as? Bool ?? false
        
        if (!aepEnabled) {
            print("[AEP Plugin] aepEnabled false")
            return
        }
        
        let appId: String = Bundle.main.object(forInfoDictionaryKey: "AEPAppId") as? String ?? ""
        
        if (aepEnabled && appId.isEmpty) {
            print("[AEP Plugin] aepEnabled but no appId found!")
        }
        
        if (aepEnabled && !appId.isEmpty) {
            implementation.load(appId: appId);
        }
    }
}
