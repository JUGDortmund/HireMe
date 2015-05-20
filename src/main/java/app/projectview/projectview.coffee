angular.module( 'projectview', ['duScroll'])
.value('duScrollDuration', 500)
.value('duScrollOffset', 30)
.config ($routeProvider) ->
  $routeProvider.when '/projectview',
    templateUrl: '/projectview/projectview.tpl.html',
    controller: 'ProjectViewCtrl', 
    resolve: 
    	projects:(Restangular) -> Restangular.all('project').getList()
.controller 'ProjectViewCtrl', ($scope, $timeout, Restangular, projects ,$document, $parse) ->
  #dateFormat = $('.datepicker').attr("data-date-format").toUpperCase()
  $scope.projects = projects
  
  $scope.add = ->
  	Restangular.one('project').post() 
  	return
  return   