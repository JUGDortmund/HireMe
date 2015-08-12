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
  'ui.bootstrap'
])
.config ($locationProvider, RestangularProvider, tagsInputConfigProvider, datepickerConfig, datepickerPopupConfig) ->
  datepickerConfig.datepickerMode = 'month'
  datepickerConfig.minMode = 'month'
  $locationProvider.html5Mode
    enabled: true
    requireBase: false
  RestangularProvider.setBaseUrl('/api/')
  tagsInputConfigProvider.setDefaults 'tagsInput',
  	placeholder: ''
  	minLength: 1
  	replaceSpacesWithDashes: false
  	allowLeftoverText: true
  .setDefaults 'autoComplete',
    minLength: 1
  return
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
  $rootScope.historyCount = 0