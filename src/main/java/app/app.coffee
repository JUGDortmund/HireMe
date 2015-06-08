angular.module('app', [
  'ngRoute'
  'dashboard'
  'profile'
  'search'
  'errors'
  'tags'
  'restangular'
])
.config ($locationProvider, RestangularProvider) ->
  $locationProvider.html5Mode
    enabled: true
    requireBase: false
  RestangularProvider.setBaseUrl('/api/')
.controller 'NavigationCtrl', ($scope, $location, Restangular) ->
  buildProperties = Restangular.one('buildProperties')
  buildProperties.get().then (data) ->
    $scope.buildProperties = data.buildProperties
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