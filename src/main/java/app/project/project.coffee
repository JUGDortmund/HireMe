angular.module( 'project', ['duScroll', 'ngTagsInput', 'ngDialog'])
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
        
.controller 'ProjectCtrl', ($scope, $timeout, Restangular, project, $document, $parse, tagService, $rootScope, ngDialog, $filter) ->
  $scope.project = project 
  $scope.openedDatepickerPopup = []
  
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
    workingProject.technologies = project.technologies.map toList
    workingProject.start = convertDate(project.start)
    workingProject.end = convertDate(project.end)
    workingProject.summary = project.summary
    Restangular.one('project', project.id).customPUT(workingProject);

  $scope.save = ->
    putWithTags(project).then (->
      $scope.showEditModeButtons = false
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
    $scope.cancelChanges = false
    return

  $scope.change = (id) ->
    $('#' + id).addClass('has-warning') if id?
    $scope.showEditModeButtons = true
    return

  $scope.tagsToList = (tags) ->
    if tags? then tags.map toList else []

  $scope.getTags = (name) ->
    tagService.getTag(name)


  $scope.openDatepickerPopup = ($event, datepicker) ->
    $event.preventDefault();
    $event.stopPropagation();
    $scope.openedDatepickerPopup.start = false
    $scope.openedDatepickerPopup.end = false
    $scope.openedDatepickerPopup[datepicker] = true;
    return
    
  $scope.removeDuplicate = (variableName, tag, field) ->
    showMessage('errorDuplicate'+ field, true) 
    $scope.textTag = tag.text
    $timeout (->
      $parse(variableName).assign($scope,'')
      return
    )
    return

  $rootScope.$on '$locationChangeStart',(event) ->
    if($scope.showEditModeButtons == true)
      event.preventDefault()
      $scope.dialog()
    return  

  $scope.dialog = ->
    ngDialog.open(
      template:'warningDialog'
      preCloseCallback: (value) ->
        if(value == '1')
          return $scope.save()
        if(value == '0')
          return $scope.cancel()  
      )
      
  convertDate = (target) ->
    return $filter('date')(target, 'yyyy-MM-dd', 'GMT+0200')