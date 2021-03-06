var myip = require('my-local-ip');

if (typeof exports === 'undefined') {
    exports = {};
}
exports.config = {
    seleniumAddress: 'http://mercus-selenium-grid.maredit.net:4444/wd/hub',
    capabilities: {
        'browserName': 'chrome'
    },
    baseUrl: getUrl(),
    allScriptsTimeout: 21000,
    specs: [
        '../e2e-tests/**/*.spec.js'
    ],
    framework: 'jasmine2',
    jasmineNodeOpts: {
        showColors: true,
        defaultTimeoutInterval: 30000,
        isVerbose: true,
        includeStackTrace: true,
        // deactivate protractor dot reporter because we use jasmine spec reporter
//        print: function() {}
    },
    mocks: {
        default: [],
        dir: 'protractor-mocks'
    },
    onPrepare: function () {
        var jasmineReporters = require('jasmine-reporters');
        jasmine.getEnv().addReporter(
            new jasmineReporters.JUnitXmlReporter({
                    consolidateAll: true,
                    filePrefix: 'xmloutput',
                    savePath: 'target/protractor-reports'
                }
            ));
//        // add jasmine spec reporter
//        var SpecReporter = require('jasmine-spec-reporter');
//        jasmine.getEnv().addReporter(
//          new SpecReporter({
//              displayStacktrace: 'summary',
//              displayPendingSpec: true,
//              displaySpecDuration: true,
//              displaySuiteNumber: true
//            }
//          ));
        require('protractor-http-mock').config = {
            rootDirectory: __dirname,
            protractorConfig: 'protractor.conf'
        };
    }
};

function getUrl() {
    var url = "http://" + myip() + ":8080/";
    console.info("|--------- Using my-local-ip resulted in: ------------|");
    console.info("|          " + url + "");
    console.info("|-----------------------------------------------------|");
    return url;
}
