angular.module 'dashboard', []
.config ($routeProvider) ->
    $routeProvider.when('/dashboard', templateUrl: 'dashboard/dashboard.tpl.html')
    .otherwise redirectTo: '/dashboard'
.controller 'SearchCtrl', ($scope, $location) ->
    $scope.submit = -> $location.path('/search').search q: $scope.search