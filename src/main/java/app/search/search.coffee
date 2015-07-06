angular.module 'search', ['utils.autofocus', 'utils.customResource']
.config ($routeProvider) ->
    $routeProvider.when '/search', 
      templateUrl: 'search/search.tpl.html',
      resolve:
      	templates: (Restangular) ->
          Restangular.all('templates').getList()
.controller 'ListCtrl', ($scope, $location, Restangular) ->
  profiles = Restangular.all('profile')
  $scope.templates = Restangular.all('templates').getList()
  console.log $scope.templates
  getProfiles = -> profiles.getList().then (profiles) ->
    $scope.profiles = profiles

  $scope.$on 'add-profile', ->
    getProfiles()

  getProfiles()

  $scope.init = ->
    $scope.search=$location.search().q
