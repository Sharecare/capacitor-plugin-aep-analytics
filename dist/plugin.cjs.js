'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const AepAnalytics = core.registerPlugin('AepAnalytics', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.AepAnalyticsWeb()),
});

class AepAnalyticsWeb extends core.WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    AepAnalyticsWeb: AepAnalyticsWeb
});

exports.AepAnalytics = AepAnalytics;
//# sourceMappingURL=plugin.cjs.js.map
