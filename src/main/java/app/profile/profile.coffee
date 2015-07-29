angular.module('profile', ['duScroll', 'ngTagsInput', 'utils.customResource', 'ngFileUpload', 'ui.bootstrap', 'ngDialog'])
.value('duScrollDuration', 500)
.value('duScrollOffset', 30)
.config ($routeProvider) ->
  $routeProvider.when '/profile', templateUrl: '/profile/profile.tpl.html'
  $routeProvider.when '/profile/:profileId',
    templateUrl: '/profile/profile.tpl.html',
    controller: 'ProfileCtrl',
    resolve:
      profile: (Restangular, $route) ->
        Restangular.one('profile', $route.current.params.profileId).get()
      projects: (Restangular) ->
        Restangular.all('project').getList()
      templates: (Restangular) ->
        Restangular.all('templates').getList()
.controller 'ProfileCtrl', ($scope, $timeout, Restangular, profile, Upload, projects, $document, $parse, tagService, $rootScope, ngDialog, $filter) ->
  $scope.profile = profile
  $scope.projects = projects
  $scope.originProfile = angular.copy($scope.profile)
  tagService.loadTags()
  $scope.openedWorkExperienceDatepickerPopup = false
  $scope.openedDatepickerPopup = []
	    
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

  putWithTags = (profile) ->
    workingProfile = {}
    workingProfile.id = profile.id
    workingProfile.firstname = profile.firstname
    workingProfile.lastname = profile.lastname
    workingProfile.degrees = profile.degrees.map toList
    workingProfile.careerLevel = profile.careerLevel.map toList
    workingProfile.workExperience = convertDate(profile.workExperience)
    workingProfile.firstMainFocus = profile.firstMainFocus
    workingProfile.secondMainFocus = profile.secondMainFocus
    workingProfile.languages = profile.languages.map toList
    workingProfile.industries = profile.industries.map toList
    workingProfile.platforms = profile.platforms.map toList
    workingProfile.opSystems = profile.opSystems.map toList
    workingProfile.progLanguages = profile.progLanguages.map toList
    workingProfile.webTechnologies = profile.webTechnologies.map toList
    workingProfile.devEnvironments = profile.devEnvironments.map toList
    workingProfile.qualifications = profile.qualifications.map toList
    workingProfile.summary = profile.summary
    workingProfile.image = profile.image
    workingProfile.projectAssociations = _.map profile.projectAssociations, (project) ->
      workingProject = {}
      workingProject.id = project.id
      workingProject.project = project.project
      workingProject.end = convertDate(project.end) if project.end?
      workingProject.start = convertDate(project.start) if project.start?
      workingProject.locations = project.locations.map toList if project.locations?
      workingProject.positions = project.positions.map toList if project.positions?
      workingProject.technologies = project.technologies.map toList if project.technologies?
      workingProject.tasks = project.tasks if project.tasks?
      return workingProject
    Restangular.one('profile', profile.id).customPUT(workingProfile);

  $scope.save = ->
    putWithTags(profile).then (->
      $scope.showEditModeButtons = false
      $scope.originProfile = angular.copy($scope.profile)
      $scope.files = []
      showMessage('success')
      tagService.loadTags()
      return
    ), ->
      showMessage('error')
      tagService.loadTags()
      return

  $scope.cancel = ->
    $scope.profile = angular.copy($scope.originProfile)
    $scope.showEditModeButtons = false
    $('.form-group').removeClass('has-warning')
    $document.duScrollTopAnimated(0)
    $scope.files = []
    tagService.loadTags()
    return

  $scope.change = (id) ->
    $('#' + id).addClass('has-warning') if id?
    $scope.showEditModeButtons = true
    $scope.cancelChanges = false
    return

  $scope.tagsToList = (tags) ->
    if tags? then tags.map toList else []

  $scope.getTags = (name) ->
    tagService.getTag(name)

  $scope.addProjectAssociation = ->
    if !$scope.profile.projectAssociations? then $scope.profile.projectAssociations = []
    $scope.profile.projectAssociations.push({});
    $scope.projectData.push({})
    $scope.projectData.push({start: false, end: false})
    $scope.change()
    return

  $scope.deleteProjectAssociation = (index) ->
    $scope.profile.projectAssociations.splice(index, 1);
    $scope.projectData.splice(index, 1)
    $scope.openedDatepickerPopup.splice(index, 1)
    $scope.change()
    return
  
  #Loads the Default values from the project if the field is empty.
  $scope.loadProjectDefaultsIfFieldIsEmpty = (index) ->
    currentProjectAssociation = $scope.profile.projectAssociations[index]
    if  testCurrentProjectAssociations(currentProjectAssociation, 'locations')
    	currentProjectAssociation.locations = currentProjectAssociation.project.locations.slice()
    if testCurrentProjectAssociations(currentProjectAssociation, 'technologies')
    	currentProjectAssociation.technologies = currentProjectAssociation.project.technologies.slice()
    if testCurrentProjectAssociations(currentProjectAssociation, 'start')
    	currentProjectAssociation.start = currentProjectAssociation.project.start
    if testCurrentProjectAssociations(currentProjectAssociation, 'end')
    	currentProjectAssociation.end = currentProjectAssociation.project.end
    return
  
  testCurrentProjectAssociations = (projectAssocation, type) ->
    return (projectAssocation.project[type] != 'undefined' && (typeof projectAssocation[type] == 'undefined' || projectAssocation[type].length == 0))

  
  #Loads the projectDefaults if the project is changed.
  $scope.loadProjectDefaults = (index) ->
    currentProjectAssociation = $scope.profile.projectAssociations[index]
    data = {}
    data.locations = currentProjectAssociation.project.locations.slice() if currentProjectAssociation.project.locations?
    data.technologies = currentProjectAssociation.project.technologies.slice() if currentProjectAssociation.project.technologies?
    data.start = currentProjectAssociation.project.start if currentProjectAssociation.project.start != 'undefined'
    data.end = currentProjectAssociation.project.end if currentProjectAssociation.project.end != 'undefined'
    $scope.projectData.splice(index, 1, data) 
    $scope.loadProjectDefaultsIfFieldIsEmpty(index)
    return
  
  #Loads the projectDefaults initial when the side is called.
  loadInitialProjectDefaults = () ->
    if $scope.profile.projectAssociations?
      $scope.projectData = $scope.profile.projectAssociations.map (project) ->
        data = {}
        data.locations = project.project.locations.slice() if project.project.locations?
        data.technologies = project.project.technologies.slice() if project.project.technologies?
        data.start = project.project.start if project.project.start?
        data.end = project.project.end if project.project.end?
        return data
      $scope.openedDatepickerPopup = $scope.profile.projectAssociations.map (project) ->
        return {start: false, end:false}
    else 
      $scope.projectData = []
    return
  
  loadInitialProjectDefaults()
  
  $scope.$watch 'files', ->
    $scope.upload $scope.files
    return

  $scope.$watch 'rejectedFiles', (newValue, oldValue) ->
    if(newValue != oldValue && $scope.rejectedFiles.length > 0)
      $scope.rejectedFile = []
      showMessage('uploadreject', true)
    return

  $scope.upload = (files) ->
    if files and files.length
      file = files[0]
      Upload.upload(
        url: '/api/resource/upload'
        file: file
        fileFormDataName: file.name)
      .success (data, status, headers, config) ->
        $timeout (->
          $scope.$apply()
          return
        )
        $scope.profile.image = data.id
        $scope.change('image-wrapper')
        return
      .error (data, status, headers, config) ->
        $timeout (->
          $scope.$apply()
          return
        )
        $scope.files = []
        showMessage('uploaderror', true)
    return
    
  $scope.openDatepickerPopup = ($event, datepicker, index) ->
    if(datepicker=='workExperience')
    	$scope.openedWorkExperienceDatepickerPopup = true;
    else
    	$event.preventDefault();
    	$event.stopPropagation();
    	$scope.workExperienceDatepickerPopup = false;
    	$scope.openedDatepickerPopup[index] =
    		start: false
    		end: false
    	$scope.openedDatepickerPopup[index][datepicker] = true;
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

  $scope.dialog = () ->
    ngDialog.open
      template:'warningDialog'
      preCloseCallback: (value) ->
        if(value == '1')
          return $scope.save()
        if(value == '0')
          return $scope.cancel()  

  convertDate = (target) ->
    date = $filter('date')(target, 'yyyy-MM-dd', 'GMT+0200')
    return date 
      
  $scope.downloadPDF = (template) ->
  	if (template == 'Anonym' && (profile.firstMainFocus== '' || profile.secondMainFocus == ''))
  	  ngDialog.openConfirm(
  	    scope: $scope
  	    template:'anonymDialog').then(() ->
  	      $scope.save().then(() ->
  	        window.open('/api/exportProfile/'+ profile.id + '/' + template, '_self' ))
  	    )
    else
      window.open('/api/exportProfile/'+ profile.id + '/' + template, '_self')
  return 