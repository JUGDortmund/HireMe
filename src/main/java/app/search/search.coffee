angular.module 'search', []
.config ($routeProvider) ->
    $routeProvider.when '/search', templateUrl: 'search/search.tpl.html'
.controller 'SearchCtrl', ($scope, $location) ->
    $scope.submit = -> $location.path('/search').search q: $scope.search
    $scope.init = ->
      $('[autofocus]:first').focus()
      return false
.controller 'ListCtrl', ($scope, Restangular) ->
  profiles = Restangular.all('profile')
  getProfiles =  -> profiles.getList().then (profiles) ->
    $scope.profiles = profiles

  $scope.$on 'add-profile', ->
    getProfiles()

  getProfiles()
