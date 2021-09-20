var capacitorAepAnalytics = (function (exports, core) {
    'use strict';

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

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

}({}, capacitorExports));
//# sourceMappingURL=plugin.js.map
