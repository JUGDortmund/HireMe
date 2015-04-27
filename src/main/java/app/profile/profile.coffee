angular.module('profile', []).config [
  '$routeProvider'
  ($routeProvider) ->
    $routeProvider.when '/profile', templateUrl: 'profile/profile.tpl.html'
]
