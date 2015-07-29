angular.module 'search', ['utils.autofocus', 'utils.customResource']
.config ($routeProvider) ->
    $routeProvider.when '/search', 
      templateUrl: 'search/search.tpl.html',
      resolve:
      	templates: (Restangular) ->
          Restangular.all('templates').getList()
.controller 'ListCtrl', ($scope, $location, Restangular) ->
  profiles = Restangular.all('profile')
  templates = Restangular.all('templates')
  getTemplates = -> templates.getList().then (templates) ->
    $scope.templates = templates
  getProfiles = -> profiles.getList().then (profiles) ->
    $scope.profiles = profiles

  $scope.$on 'add-profile', ->
    getProfiles()

  getProfiles()
  getTemplates()

  $scope.init = ->
    $scope.search=$location.search().q
