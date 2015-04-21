exports.config = {
    multiCapabilities: [{
        browserName: 'firefox'
    }, {
        browserName: 'chrome'
    }],
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
