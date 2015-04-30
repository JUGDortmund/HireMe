angular.module('errors', []).config [
  '$routeProvider'
  ($routeProvider) ->
    $routeProvider.when '/400', templateUrl: 'errors/400.tpl.html'
    $routeProvider.when '/401', templateUrl: 'errors/401.tpl.html'
    $routeProvider.when '/403', templateUrl: 'errors/403.tpl.html'
    $routeProvider.when '/404', templateUrl: 'errors/404.tpl.html'
    $routeProvider.when '/500', templateUrl: 'errors/500.tpl.html'
]
