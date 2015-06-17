angular.module( 'project', ['duScroll', 'ngTagsInput'])
.value('duScrollDuration', 500)
.value('duScrollOffset', 30)
.config ($routeProvider) ->
  $routeProvider.when '/project', templateUrl: '/project/project.tpl.html'
  $routeProvider.when '/project/:projectId',
    templateUrl: '/project/project.tpl.html',
    controller: 'ProjectCtrl',
    resolve:
      project: (Restangular, $route) ->
        Restangular.one('project', $route.current.params.projectId).get()
.controller 'ProjectCtrl', ($scope, $timeout, Restangular, project, $document, $parse, tagService) ->
  dateFormat = $('.datepicker').attr("data-date-format").toUpperCase()
  $scope.project = project 
  if moment(project.start).isValid()
    $scope.project.start = moment(project.start).format(dateFormat)
  else
    $scope.project.start = ""
  if moment(project.end).isValid()
    $scope.project.end = moment(project.end).format(dateFormat)
  else
    $scope.project.end = ""  
  $scope.originProject = angular.copy($scope.project)       
  tagService.loadTags()
  
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

  toList = (x) ->
    x.text

  putWithTags = (project) ->
    workingProject = {}

    workingProject.id = project.id
    workingProject.title = project.title
    workingProject.companies = project.companies.map toList
    workingProject.locations = project.locations.map toList
    workingProject.industries = project.industries.map toList
    workingProject.start = project.start
    workingProject.end = project.end
    workingProject.summary = project.summary

    Restangular.one('project', project.id).customPUT(workingProject);

  $scope.save = ->
    dateFormat = $('.datepicker').attr("data-date-format").toUpperCase()
    dateString = moment($scope.project.start, dateFormat).toDate();
    $scope.project.start = dateString
    dateString = moment($scope.project.end, dateFormat).toDate();
    $scope.project.end = dateString
    putWithTags(project).then (->
      $scope.showEditModeButtons = false
      $scope.project.start = moment($scope.project.start).format(dateFormat);
      $scope.project.end = moment($scope.project.end).format(dateFormat);
      $scope.originProject = angular.copy($scope.project)
      showMessage('success')
      tagService.loadTags()
      return
    ), ->
      showMessage('error')
      tagService.loadTags()
      return

  $scope.cancel = ->
    $scope.project = angular.copy($scope.originProject)
    $scope.showEditModeButtons = false
    $('.form-group').removeClass('has-warning')
    $document.duScrollTopAnimated(0)
    tagService.loadTags()
    return

  $scope.change = (id) ->
    $('#' + id).addClass('has-warning')
    $scope.showEditModeButtons = true
    return

  $scope.getTags = (name) ->
    tagService.getTag(name)
    return
    
  $scope.removeDuplicate = (variableName,tag) ->
  	showMessage('errorDuplicate')
  	$scope.textTag = tag.text
  	$parse(variableName).assign($scope,'')
  	return