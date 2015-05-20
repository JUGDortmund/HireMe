/**
 * Config file for running protractor locally
 */
var baseConfig = require('./protractor.conf.js');
var config = baseConfig.config;

// override base config properties
config.seleniumAddress = undefined;
config.capabilities = {
        'browserName': 'firefox'
};

// export config
exports.config = config;