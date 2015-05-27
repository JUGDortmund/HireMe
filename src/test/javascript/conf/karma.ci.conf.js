module.exports = function (config) {
    config.set({
        basePath: '../../../../',
        frameworks: ['jasmine'],
        files: [
            'http://localhost:8080/webjars/angularjs/1.3.8/angular.js',
            'http://localhost:8080/webjars/angularjs/1.3.8/angular-mocks.js',
            'http://localhost:8080/webjars/angularjs/1.3.8/angular-route.js',
            'http://localhost:8080/webjars/ng-tags-input/2.3.0/ng-tags-input.min.js',
            'http://localhost:8080/webjars/restangular/1.5.1/dist/restangular.min.js',
            'http://localhost:8080/webjars/angular-scroll/0.7.0/angular-scroll.js',
            'http://localhost:8080/webjars/lodash/3.7.0/lodash.min.js',
            'http://localhost:8080/webjars/ng-file-upload/4.2.0/ng-file-upload.min.js',
            'http://localhost:8080//webjars/underscorejs/1.8.3/underscore-min.js',
            'src/main/java/app/**/*.js',
            'src/main/java/assets/javascript/**/*.js',
            'src/main/java/app/**/*.coffee',
            'src/test/javascript/**/*.js',
            'src/test/javascript/**/*.coffee'
        ],
        exclude: [
            '**/conf/**',
            '**/e2e-tests/**'
        ],
        preprocessors: {
            '**/*.coffee': ['coffee']
        },
        coffeePreprocessor: {
            options: {
                bare: true
            }
        },
        transformPath: function (path) {
            return path.replace(/\.coffee$/, '.js')
        },
        port: 9876,
        colors: true,
        logLevel: config.LOG_INFO,
        browsers: ['PhantomJS'],
        reporters: ['progress', 'junit'],
        singleRun: true,
        autoWatch: true,
        plugins: ['karma-jasmine',
                  'karma-phantomjs-launcher',
                  'karma-junit-reporter',
                  'karma-coffee-preprocessor'
        ],
        junitReporter: {
            outputFile: 'target/surefire-reports/js-tests.xml',
            suite: 'js-tests'
        }
    });
    updateWebjarPaths(config);
};

function updateWebjarPaths(config) {
    for (var i = 0; i < config.files.length; i++) {
        config.files[i] = config.files[i].replace('http://localhost:8080/webjars',
                                                  'target/dependency/META-INF/resources/webjars')
    }
}
