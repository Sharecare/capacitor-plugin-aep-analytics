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
        implementation.load();
    }
}
