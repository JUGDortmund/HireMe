module.exports = function(config) {
  config.set({
    basePath: '../../../../',
    frameworks: ['jasmine'],
    files: [
      'http://localhost:8080/webjars/angularjs/1.3.8/angular.js',
      'http://localhost:8080/webjars/angularjs/1.3.8/angular-mocks.js',
      'http://localhost:8080/webjars/angularjs/1.3.8/angular-route.js',
      'src/main/java/app/**/*.js',
      'src/test/javascript/**/*.js'
    ],
    exclude: [
      '**/conf/**',
      '**/e2e-tests/**'
    ],
    preprocessors: {},
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    browsers: ['PhantomJS'],
    reporters: ['progress', 'junit'],
    singleRun: true,
    autoWatch: true,
    plugins: ['karma-jasmine', 'karma-phantomjs-launcher',
        'karma-junit-reporter'],
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
