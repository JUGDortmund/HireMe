angular.module 'search', []
.config ($routeProvider) ->
    $routeProvider.when '/search', templateUrl: 'search/search.tpl.html'
