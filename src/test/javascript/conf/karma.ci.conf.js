var baseConfig = require('./karma.conf.js');

module.exports = function (config) {
    baseConfig(config);
    updateWebjarPaths(config);
    config.set({
        browsers: ['PhantomJS'],
        reporters: ['progress', 'junit'],
        singleRun: true,
        autoWatch: true,
        plugins: [
            'karma-jasmine',
            'karma-phantomjs-launcher',
            'karma-junit-reporter'
        ],
        junitReporter: {
            outputFile: 'target/surefire-reports/js-tests.xml',
            suite: 'js-tests'
        }
    });
};

function updateWebjarPaths(config) {
    for (var i = 0; i < config.files.length; i++) {
        config.files[i] = config.files[i].replace(
            'http://localhost:8080/webjars',
            'target/dependency/META-INF/resources/webjars'
        )
    }

}