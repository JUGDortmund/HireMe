angular.module('app', [
    'ngRoute',
    'dashboard',
    'profile',
    'search'
])
    .config(function ($locationProvider) {
                $locationProvider.html5Mode({
                    enabled: true,
                    requireBase: false
                });
            });
