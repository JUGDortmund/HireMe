angular.module('app', [
  'ngRoute'
  'dashboard'
  'profile'
  'search'
]).config(($locationProvider) ->
  $locationProvider.html5Mode
    enabled: true
    requireBase: false
).controller('NavigationCtrl', ($scope, $location) ->

  $scope.isActive = (route) ->
    route == $location.path()
).controller 'HeaderCtrl', ($scope) ->
  $scope.init = ->
    $.AdminLTE.pushMenu '[data-toggle=\'offcanvas\']'
