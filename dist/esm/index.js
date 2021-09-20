import { registerPlugin } from '@capacitor/core';
const AepAnalytics = registerPlugin('AepAnalytics', {
    web: () => import('./web').then(m => new m.AepAnalyticsWeb()),
});
export * from './definitions';
export { AepAnalytics };
//# sourceMappingURL=index.js.map