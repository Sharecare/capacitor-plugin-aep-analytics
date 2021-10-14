package com.mindsciences.plugins.aep;

import com.getcapacitor.Plugin;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "AepAnalytics")
public class AepAnalyticsPlugin extends Plugin {

    private AepAnalytics implementation = new AepAnalytics();

    @Override
    public void load() {
        Boolean aepEnabled = this.getContext().getResources().getBoolean(R.bool.aep_enabled);
        if (!aepEnabled) {
            System.out.println("### AepAnalytics - Not enabled\n");
            return;
        }

        String aepAppId = this.getContext().getString(R.string.aep_app_id);

        if (aepAppId.isEmpty()) {
            System.out.println("### AepAnalytics - Enabled but aepAppId is empty\n");
            return;
        }

        implementation.load(this.getContext(), this.getActivity(), aepAppId);
    }
}
