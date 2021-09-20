import { WebPlugin } from '@capacitor/core';
import type { AepAnalyticsPlugin } from './definitions';
export declare class AepAnalyticsWeb extends WebPlugin implements AepAnalyticsPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
}
