import { WebPlugin } from '@capacitor/core';

import type { AepAnalyticsPlugin } from './definitions';

export class AepAnalyticsWeb extends WebPlugin implements AepAnalyticsPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
