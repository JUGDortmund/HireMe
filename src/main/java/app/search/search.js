angular.module('search', [])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/search', {
            templateUrl: 'search/search.tpl.html'
        });
    }]);