angular.module('profile', ['duScroll', 'ngTagsInput', 'utils.customResource', 'ngFileUpload'])
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
.controller 'ProfileCtrl', ($scope, $timeout, Restangular, profile, Upload, projects, $document, $parse, tagService) ->
  dateFormat = $('.datepicker').attr("data-date-format").toUpperCase()
  $scope.profile = profile
  $scope.projects = projects
  if moment(profile.workExperience).isValid()
    $scope.profile.workExperience = moment(profile.workExperience).format(dateFormat)
  else
    $scope.profile.workExperience = ""
  $scope.originProfile = angular.copy($scope.profile)
  tagService.loadTags()
  
  showMessage = (targetName, keepChangeIndicators) ->
    target = $parse(targetName)
    target.assign($scope, true)
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
    workingProfile.workExperience = profile.workExperience
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
      workingProject.end = project.end if project.end?
      workingProject.start = project.start if project.start?
      workingProject.locations = project.locations.map toList
      workingProject.positions = project.positions.map toList
      workingProject.technologies = project.technologies.map toList
      workingProject.tasks = project.tasks if project.tasks?
      return workingProject
    Restangular.one('profile', profile.id).customPUT(workingProfile);

  $scope.save = ->
    dateFormat = $('.datepicker').attr("data-date-format").toUpperCase()
    dateString = moment($scope.profile.workExperience, dateFormat).toDate();
    $scope.profile.workExperience = dateString
    putWithTags(profile).then (->
      $scope.showEditModeButtons = false
      $scope.profile.workExperience = moment($scope.profile.workExperience).format(dateFormat);
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
    $scope.files = []
    $('.form-group').removeClass('has-warning')
    $('#image-wrapper').removeClass('has-warning')
    $document.duScrollTopAnimated(0)
    tagService.loadTags()
    return

  $scope.change = (id) ->
    $('#' + id).addClass('has-warning') if id?
    $scope.showEditModeButtons = true
    return

  $scope.tagsToList = (tags) ->
    if tags? then tags.map toList else []

  $scope.getTags = (name) ->
    tagService.getTag(name)

  $scope.addProjectAssociation = ->
    if !$scope.profile.projectAssociations? then $scope.profile.projectAssociations = []
    $scope.profile.projectAssociations.push({});
    $scope.projectData.push({})
    $scope.change()
    return

  $scope.deleteProjectAssociation = (index) ->
    $scope.profile.projectAssociations.splice(index, 1);
    $scope.projectData.splice(index, 1)
    $scope.change()
    return
  
  #Loads the Default values from the project if the field is empty.
  $scope.loadProjectDefaultsIfFieldIsEmpty = (index) ->
    currentProjectAssociation = $scope.profile.projectAssociations[index]
    if currentProjectAssociation.locations? && currentProjectAssociation.locations.length == 0
    	currentProjectAssociation.locations = currentProjectAssociation.project.locations.slice()
    if currentProjectAssociation.technologies? && currentProjectAssociation.technologies.length == 0
    	currentProjectAssociation.technologies = currentProjectAssociation.project.technologies.slice()
    if typeof currentProjectAssociation.start == 'undefined' || currentProjectAssociation.start== ""
    	currentProjectAssociation.start = moment(currentProjectAssociation.project.start).format(dateFormat)
    if typeof currentProjectAssociation.end == 'undefined' || currentProjectAssociation.end== ""
    	currentProjectAssociation.end = moment(currentProjectAssociation.project.end).format(dateFormat)
    return
  
  #Loads the projectDefaults if the project is changed.
  $scope.loadProjectDefaults = (index) ->
    currentProjectAssociation = $scope.profile.projectAssociations[index]
    data = {}
    data.locations = currentProjectAssociation.project.locations.slice() if currentProjectAssociation.project.locations?
    data.technologies = currentProjectAssociation.project.technologies.slice() if currentProjectAssociation.project.technologies?
    data.start = moment(currentProjectAssociation.project.start).format(dateFormat) if typeof currentProjectAssociation.project.start != 'undefined'
    data.end = moment(currentProjectAssociation.project.end).format(dateFormat) if typeof currentProjectAssociation.project.end != 'undefined'
    $scope.projectData.splice(index, 1, data) 
    $scope.loadProjectDefaultsIfFieldIsEmpty(index)
    return
  
  #Loads the projectDefaults initial when the side is called.
  loadInitialProjectDefaults = () ->
    if $scope.profile.projectAssociations?
      console.log($scope.profile.projectAssociations)
      $scope.projectData = $scope.profile.projectAssociations.map (project) ->
        data = {}
        data.locations = project.locations.slice() if project.locations?
        data.technologies = project.technologies.slice() if project.technologies?
        data.start = moment(project.start).format(dateFormat) if project.start?
        data.end = moment(project.end).format(dateFormat) if project.end?
        return data
      console.log($scope.projectData)
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