angular.module('profile', [])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/profile', {
            templateUrl: 'profile/profile.tpl.html'
        });
    }]);