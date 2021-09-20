import { registerPlugin } from '@capacitor/core';

import type { AepAnalyticsPlugin } from './definitions';

const AepAnalytics = registerPlugin<AepAnalyticsPlugin>('AepAnalytics', {
  web: () => import('./web').then(m => new m.AepAnalyticsWeb()),
});

export * from './definitions';
export { AepAnalytics };
