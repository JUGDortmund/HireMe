angular.module('app', [
    'ngRoute',
    'dashboard',
    'profile',
    'search'
])
    .config(['$locationProvider'], function ($locationProvider) {
                $locationProvider.html5Mode({
                    enabled: true,
                    requireBase: false
                });
            })
    .controller('NavigationCtrl', function ($scope, $location) {
                    $scope.isActive = function (route) {
                        return route === $location.path();
                    };

                })
    .controller('HeaderCtrl', function ($scope) {
                    $scope.init = function () {
                        $.AdminLTE.pushMenu("[data-toggle='offcanvas']");
                    };
                });
