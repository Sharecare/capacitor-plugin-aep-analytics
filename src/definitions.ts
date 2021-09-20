export interface AepAnalyticsPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
