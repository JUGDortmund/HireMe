angular.module 'profile', ['duScroll', 'ngFileUpload', 'utils.customResource']
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
.controller 'ProfileCtrl', ($scope, $timeout, $document, $parse, Restangular, profile, Upload) ->
  $scope.profile = profile
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
      "profile.summary",
      "profile.image"
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
    profile.put().then (->
      $scope.showEditModeButtons = false
      $scope.originProfile = angular.copy($scope.profile)
      $scope.files = []
      showMessage('success')
      return
    ), ->
      showMessage('error')
      return

  $scope.cancel = ->
    $scope.editMode = false
    $scope.profile = angular.copy($scope.originProfile)
    $scope.showEditModeButtons = false
    $scope.files = []
    $('.form-group').removeClass('has-warning')
    $document.duScrollTopAnimated(0)
    return

  $scope.change = (id) ->
    $('#' + id).addClass('has-warning')
    return

  $scope.$watch 'files', ->
    $scope.upload $scope.files
    return
  
  $scope.upload = (files) ->
    if files and files.length
      i = 0
      while i < files.length
        file = files[i]
        Upload.upload(
          url: '/api/resource/upload'
          file: file).progress((evt) ->
          progressPercentage = parseInt(100.0 * evt.loaded / evt.total)
          return
        ).success (data, status, headers, config) ->
          $timeout (->
            $scope.$apply()
            return
          )
          $scope.enableEditMode()
          $scope.profile.image = data.id
          return
        i++
    return
