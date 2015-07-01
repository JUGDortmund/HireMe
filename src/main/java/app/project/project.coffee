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
.controller 'ProjectCtrl', ($scope, $timeout, $interval, Restangular, project, $document, $parse, tagService) ->
  $scope.project = project
  $scope.openedWorkexperience = false;
  $scope.opened = []
  

  if project.start != undefined
    $scope.project.start = project.start
  else
    $scope.project.start = ""
  if project.end != undefined
    $scope.project.end = project.end
  else
    $scope.project.end = ""  
  $scope.originProject = angular.copy($scope.project)       
  tagService.loadTags()
  
  $scope.opened =
    start: false
    end: false
    
  showMessage = (targetName, keepChangeIndicators) ->
    target = $parse(targetName)
    target.assign($scope, true)

    if (targetName == 'success' || targetName == 'error')
      $document.duScrollTopAnimated(0)
    $('.form-group').removeClass('has-warning') unless keepChangeIndicators?
    $('#image-wrapper').removeClass('has-warning') unless keepChangeIndicators?
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
    dateString = $scope.project.start
    $scope.project.start = dateString
    dateString = $scope.project.end
    $scope.project.end = dateString
    putWithTags(project).then (->
      $scope.showEditModeButtons = false
      $scope.project.start = $scope.project.start
      $scope.project.end = $scope.project.end
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
    
  $scope.open = ($event, datepicker) ->
    $event.preventDefault();
    $event.stopPropagation();
    $scope.opened[datepicker] = true;
    return

  return
    
  $scope.removeDuplicate = (variableName, tag, field) ->
    showMessage('errorDuplicate'+ field, true) 
    $scope.textTag = tag.text
    $timeout (->
      $parse(variableName).assign($scope,'')
      return
    )
    return