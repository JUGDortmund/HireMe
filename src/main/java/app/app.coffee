angular.module 'app', ['ngRoute', 'dashboard', 'profile', 'search', 'restangular']
.config ($locationProvider, RestangularProvider) ->
  $locationProvider.html5Mode
    enabled: true
    requireBase: false
    RestangularProvider.setBaseUrl('/api/')
.controller 'NavigationCtrl', ($scope, $location) ->
  $scope.isActive = (route) ->
    route == $location.path()
.controller 'HeaderCtrl', ($scope, $rootScope, Restangular) ->
  $scope.init = ->
    $.AdminLTE.pushMenu '[data-toggle=\'offcanvas\']'
  $scope.addProfile = ->
    baseProfile = Restangular.all('profile')
    baseProfile.post()
    $rootScope.$broadcast('add-profile');
