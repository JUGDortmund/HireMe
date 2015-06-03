angular.module('app', [
  'ngRoute'
  'dashboard'
  'profile'
  'search'
  'errors'
  'tags'
  'restangular'
  'project'
  'projectList'
  'ngTagsInput'
])
.config ($locationProvider, RestangularProvider, tagsInputConfigProvider) ->
  $locationProvider.html5Mode
    enabled: true
    requireBase: false
  RestangularProvider.setBaseUrl('/api/')
  tagsInputConfigProvider.setDefaults 'tagsInput',
  	minLength: 1
  return
.controller 'NavigationCtrl', ($scope, $location, Restangular) ->
  gitProperties = Restangular.one('gitProperties')
  gitProperties.get().then (data) ->
    $scope.gitPropertyDTO = data.getGitPropertyDTO
  $scope.isActive = (route) ->
    route is $location.path()
  $scope.triggerBuildInformation = ->
    liBuildInformation = angular.element(document.querySelector('#li-build-information'))
    liBuildInformation.toggleClass 'active'
    false
.controller 'HeaderCtrl', ($scope, $rootScope, Restangular) ->
  $scope.init = ->
    $.AdminLTE.pushMenu '[data-toggle=\'offcanvas\']'
    $.AdminLTE.layout.activate();
  $scope.addProfile = ->
    baseProfile = Restangular.all('profile')
    baseProfile.post().then ->
      $rootScope.$broadcast('add-profile');