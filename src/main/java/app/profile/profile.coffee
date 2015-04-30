angular.module 'profile', []
.config ($routeProvider) ->
  $routeProvider.when '/profile', templateUrl: 'profile/profile.tpl.html'
    $routeProvider.when '/profile/:profileId', templateUrl: 'profile/profile.tpl.html'
.controller 'ProfileCtrl', ($scope, $routeParams, Restangular) ->
  $scope.profileId = $routeParams.profileId
