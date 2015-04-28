angular.module('app', [
  'ngRoute'
  'dashboard'
  'profile'
  'search'
  'restangular'
]).config(($locationProvider, RestangularProvider) ->
  $locationProvider.html5Mode
    enabled: true
    requireBase: false
         ).controller('NavigationCtrl', ($scope, $location, $http) ->
  $http.get('/api/getGitProperties').success((data, status, headers, config) ->
                                               $scope.gitPropertyDTO = data.gitPropertyDTO
                                            ).error (data, status, headers, config) ->
    console.log 'ERROR'
   RestangularProvider.setBaseUrl('/api/')
).controller('NavigationCtrl', ($scope, $location) ->
  $scope.isActive = (route) ->
    route is $location.path()
  $scope.triggerBuildInformation = ->
    liBuildInformation = angular.element(document.querySelector('#li-build-information'))
    liBuildInformation.toggleClass 'active'
).controller 'HeaderCtrl', ($scope, $rootScope, Restangular) ->
  $scope.init = ->
    $.AdminLTE.pushMenu '[data-toggle=\'offcanvas\']'
  $scope.addProfile = ->
    baseProfile = Restangular.all('profile')
    baseProfile.post()
    $rootScope.$broadcast('add-profile');
