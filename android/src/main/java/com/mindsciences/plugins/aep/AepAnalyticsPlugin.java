package com.mindsciences.plugins.aep;

import com.getcapacitor.Plugin;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "AepAnalytics")
public class AepAnalyticsPlugin extends Plugin {

    private AepAnalytics implementation = new AepAnalytics();

    @Override
    public void load() {

        int aepEnabledIdentifier = getResourceId("aep_enabled", "bool");
        int aepAppIdIdentifier = getResourceId("aep_app_id", "string");

        boolean aepEnabled = this.getContext().getResources().getBoolean(aepEnabledIdentifier);
        if (!aepEnabled) {
            System.out.println("### AepAnalytics - Not enabled\n");
            return;
        }

        String aepAppId = this.getContext().getString(aepAppIdIdentifier);

        if (aepAppId.isEmpty()) {
            System.out.println("### AepAnalytics - Enabled but aepAppId is empty\n");
            return;
        }

        implementation.load(this.getContext(), this.getActivity(), aepAppId);
    }

    private int getResourceId(String name, String type) {
        String qualifiedName = getContext().getPackageName() + ":" + type + "/" + name;
        return getContext()
                .getResources()
                .getIdentifier(qualifiedName, null, null);
    }
}