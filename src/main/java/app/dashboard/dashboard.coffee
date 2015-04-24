angular.module('dashboard', []).config(['$routeProvider',
  ($routeProvider) ->
    $routeProvider.when('/dashboard', templateUrl: 'dashboard/dashboard.tpl.html')
    .otherwise redirectTo: '/dashboard'
]).controller 'SearchController', ($scope, $location) ->
  $scope.submit = ->
    $location.path('/search').search q: $scope.search