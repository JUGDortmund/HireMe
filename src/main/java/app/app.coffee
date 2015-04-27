angular.module('app', [
  'ngRoute'
  'dashboard'
  'profile'
  'search'
]).config(($locationProvider) ->
  $locationProvider.html5Mode
    enabled: true
    requireBase: false
         ).controller('NavigationCtrl', ($scope, $location, $http) ->
  $http.get('/api/getGitProperties').success((data, status, headers, config) ->
                                               $scope.gitPropertyDTO = data.gitPropertyDTO
                                            ).error (data, status, headers, config) ->
    console.log 'ERROR'
  $scope.isActive = (route) ->
    route is $location.path()
  $scope.triggerBuildInformation = ->
    liBuildInformation = angular.element(document.querySelector('#li-build-information'))
    liBuildInformation.toggleClass 'active'
).controller 'HeaderCtrl', ($scope) ->
  $scope.init = ->
    $.AdminLTE.pushMenu '[data-toggle=\'offcanvas\']'
