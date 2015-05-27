angular.module 'profile', ['duScroll', 'ngTagsInput', 'ngFileUpload', 'utils.customResource']
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
.controller 'ProfileCtrl', ($scope, $timeout, Restangular, profile, projects, $document, $parse, tagService) ->

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
    workingProfile.projectAssociations = profile.projectAssociations
    workingProfile.image = profile.image

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
    $('#' + id).addClass('has-warning')
    $scope.showEditModeButtons = true
    return

  $scope.tagsToList = (tags) ->
    if tags? then tags.map toList else []

  $scope.getTags = (name) ->
    tagService.getTag(name)

  $scope.addProjectAssociation = ->
    if !$scope.profile.projectAssociations? then $scope.profile.projectAssociations = []
    $scope.profile.projectAssociations.push({});
    console.log($scope.profile);
    
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

