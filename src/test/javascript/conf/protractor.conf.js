if (typeof exports === 'undefined') {
    exports = {};
}
exports.config = {
    capabilities: {
        'browserName': 'phantomjs',
        'phantomjs.binary.path': require('phantomjs').path,
        'phantomjs.ghostdriver.cli.args': ['--loglevel=DEBUG']
    },
    baseUrl: 'http://localhost:8080/',
    allScriptsTimeout: 21000,
    specs: [
        '../e2e-tests/**/*.js'
    ],
    framework: 'jasmine2',
    jasmineNodeOpts: {
        showColors: true,
        defaultTimeoutInterval: 30000
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
    }
};
