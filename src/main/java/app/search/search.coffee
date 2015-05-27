angular.module 'search', ['utils.autofocus', 'utils.customResource']
.config ($routeProvider) ->
    $routeProvider.when '/search', templateUrl: 'search/search.tpl.html'
.controller 'ListCtrl', ($scope, $location, Restangular) ->
  profiles = Restangular.all('profile')
  getProfiles = -> profiles.getList().then (profiles) ->
    $scope.profiles = profiles

  $scope.$on 'add-profile', ->
    getProfiles()

  getProfiles()

  $scope.init = ->
    $scope.search=$location.search().q
