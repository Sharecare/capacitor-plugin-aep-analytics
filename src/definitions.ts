declare module '@capacitor/core' {
  interface PluginRegistry {
    AepAnalytics: AepAnalyticsPlugin;
  }
}

export interface AepAnalyticsPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
