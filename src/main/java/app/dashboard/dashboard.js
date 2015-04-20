angular.module('dashboard', [])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/dashboard', {
            templateUrl: 'dashboard/dashboard.tpl.html'
        })
            .otherwise({
                redirectTo: '/dashboard'
            });
    }]);