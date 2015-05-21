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
  getProjects = -> projects.getList().then (projects) ->
    $scope.projects = projects
  
  showMessage = (targetName) ->
    target = $parse(targetName)
    target.assign($scope, true)
    $document.duScrollTopAnimated(0)
    $('.form-group').removeClass('has-warning')
    $timeout (->
      target.assign($scope, false)
      return
    ), 6000
    return
  
  $scope.add = ->
  	Restangular.one('project').post().then ->
  	  getProjects()
  	return
  
  $scope.remove = (id) ->
  	Restangular.one('project', id).remove().then (->
  	  getProjects()
  	  showMessage('success')
  	  ), ->
  	  showMessage('error')
  	return
  return