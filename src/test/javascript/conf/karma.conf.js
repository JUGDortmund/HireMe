module.exports = function (config) {
    config.set({
        basePath: '../../../../',
        frameworks: ['jasmine'],
        files: [
            'http://localhost:8080/webjars/angularjs/1.3.8/angular.js',
            'http://localhost:8080/webjars/angularjs/1.3.8/angular-mocks.js',
            'http://localhost:8080/webjars/angularjs/1.3.8/angular-route.js',
            'src/main/java/app/dist/main.js',
            'src/test/javascript/**/*.js'
        ],
        exclude: [
            '**/karma.*.js',
            '**/e2e-tests/**'
        ],
        preprocessors: {},
        reporters: ['progress'],
        port: 9876,
        colors: true,
        logLevel: config.LOG_INFO,
        autoWatch: true,
        browsers: ['Chrome'],
        singleRun: false
    });
};