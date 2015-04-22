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
        '../e2e-tests/dashboard.js'
    ],
    framework: 'jasmine',
    jasmineNodeOpts: {
        showColors: true,
        defaultTimeoutInterval: 30000
    }
};
