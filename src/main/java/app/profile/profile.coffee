angular.module( 'profile', ['duScroll'])
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
.controller 'ProfileCtrl', ($scope, $timeout, Restangular, profile, $document, $parse) ->
  dateFormat = $('.datepicker').attr("data-date-format").toUpperCase()
  $scope.profile = profile
  if moment(profile.workExperience).isValid()
    $scope.profile.workExperience = moment(profile.workExperience).format(dateFormat)
  else
    $scope.profile.workExperience = ""
  $scope.originProfile = angular.copy($scope.profile)

  $scope.$watchGroup [
      "profile.firstname",
      "profile.lastname",
      "profile.degree",
      "profile.careerStage",
      "profile.workExperience",
      "profile.languages",
      "profile.industry",
      "profile.platforms",
      "profile.opSystems",
      "profile.progLanguages",
      "profile.webTechnologies",
      "profile.devEnvironments",
      "profile.qualifications",
      "profile.summary"
    ], (newValue, oldValue) ->
      if (newValue != oldValue && $scope.editMode)
        $scope.showEditModeButtons = true
       
  $scope.enableEditMode = ->
    $scope.editMode = true
    return

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

  $scope.save = ->
    dateFormat = $('.datepicker').attr("data-date-format").toUpperCase()
    dateString=  moment($scope.profile.workExperience, dateFormat).toDate();
    $scope.profile.workExperience = dateString
    console.log $scope.profile.workExperience
    profile.put().then (->
      $scope.showEditModeButtons = false
      $scope.profile.workExperience = moment($scope.profile.workExperience).format(dateFormat);
      $scope.originProfile = angular.copy($scope.profile)
      showMessage('success')
      return
    ), ->
      showMessage('error')
      return

  $scope.cancel = ->
    $scope.editMode = false
    $scope.profile = angular.copy($scope.originProfile)
    $scope.showEditModeButtons = false
    $('.form-group').removeClass('has-warning')
    $document.duScrollTopAnimated(0)
    return

  $scope.change = (id) ->
    $('#' + id).addClass('has-warning')
    return






    
