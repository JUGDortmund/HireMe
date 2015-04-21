angular.module('dashboard', [])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/dashboard', {
            templateUrl: 'dashboard/dashboard.tpl.html'
        })
            .otherwise({
                redirectTo: '/dashboard'
            });
    }])
    .controller('SearchController', function ($scope, $location) {
                    $scope.submit = function () {
                        $location.path('/search').search({q: $scope.search});
                    }
                })
;

