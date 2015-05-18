angular.module('profile', ['duScroll', 'ngTagsInput'])
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
.controller 'ProfileCtrl', ($scope, $timeout, Restangular, profile, $document, $parse, tagService) ->
  dateFormat = $('.datepicker').attr("data-date-format").toUpperCase()
  $scope.profile = profile
  if moment(profile.workExperience).isValid()
    $scope.profile.workExperience = moment(profile.workExperience).format(dateFormat)
  else
    $scope.profile.workExperience = ""
  $scope.originProfile = angular.copy($scope.profile)
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

    Restangular.one('profile', profile.id).customPUT(workingProfile);

  $scope.save = ->
    putWithTags(profile).then (->
      $scope.showEditModeButtons = false
      dateFormat = $('.datepicker').attr("data-date-format").toUpperCase()
      dateString=  moment($scope.profile.workExperience, dateFormat).toDate();
      $scope.profile.workExperience = dateString
      $scope.profile.workExperience = moment($scope.profile.workExperience).format(dateFormat);
      $scope.originProfile = angular.copy($scope.profile)
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
    tagService.loadTags()
    return

  $scope.change = (id) ->
    $('#' + id).addClass('has-warning')
    $scope.showEditModeButtons = true
    return

  $scope.tagsToList = (tags) ->
    tags.map toList

  $scope.getTags = (name) ->
    tagService.getTag(name)