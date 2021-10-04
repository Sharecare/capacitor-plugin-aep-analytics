package com.mindsciences.plugins.aep;

import com.getcapacitor.Plugin;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "AepAnalytics")
public class AepAnalyticsPlugin extends Plugin {

    private AepAnalytics implementation = new AepAnalytics();

    @Override
    public void load() {
        implementation.load(this.getContext(), this.getActivity());
    }
}
