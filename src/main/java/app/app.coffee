angular.module('app', [
  'ngRoute'
  'dashboard'
  'profile'
  'search',
  'restangular'
]).config(($locationProvider) ->
  $locationProvider.html5Mode
    enabled: true
    requireBase: false
  Restangular.BaseUrl('/api/')
).controller('NavigationCtrl', ($scope, $location) ->
  $scope.isActive = (route) ->
    route == $location.path()
).controller 'HeaderCtrl', ($scope, Restangular) ->
  $scope.init = ->
    $.AdminLTE.pushMenu '[data-toggle=\'offcanvas\']'
  $scope.addProfile = ->
    Restangular.post('profile')
