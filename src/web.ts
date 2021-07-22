import { WebPlugin } from '@capacitor/core';
import { AepAnalyticsPlugin } from './definitions';

export class AepAnalyticsWeb extends WebPlugin implements AepAnalyticsPlugin {
  constructor() {
    super({
      name: 'AepAnalytics',
      platforms: ['web'],
    });
  }

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}

const AepAnalytics = new AepAnalyticsWeb();

export { AepAnalytics };

import { registerWebPlugin } from '@capacitor/core';
registerWebPlugin(AepAnalytics);
