angular.module('search', []).config(
  ($routeProvider) ->
    $routeProvider.when '/search', templateUrl: 'search/search.tpl.html'
).controller 'ListCtrl', ($scope, Restangular) ->
  profiles = Restangular.all('profile')
  getProfiles =  -> profiles.getList().then (profiles) ->
    $scope.profiles = profiles

  $scope.$on 'add-profile', ->
    getProfiles()

  getProfiles()
