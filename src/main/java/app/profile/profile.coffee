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
.controller 'ProfileCtrl', ($scope, $timeout, Restangular, profile, $document) ->
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
      "profile.summary"
    ], (newValue, oldValue) ->
      if (newValue != oldValue && $scope.editMode)
        $scope.showme = true
       
  $scope.enableEditMode = ->
  	$scope.editMode = true

  $scope.save = ->
    profile.put().then (->
      $scope.showme = false
      $document.duScrollTopAnimated(0)
      $('.form-group').removeClass('has-warning')
      $scope.originProfile = angular.copy($scope.profile)
      $scope.success = true
      $timeout (->
        $scope.success = false

      ), 10000

    ), ->
      $scope.error = true
      $document.duScrollTopAnimated(0)
      $('.form-group').removeClass('has-warning')
      $timeout (->
        $scope.error = false

      ), 10000


  $scope.cancel = ->
    $scope.editMode = false
    $scope.profile = angular.copy($scope.originProfile)
    $scope.showme = false
    $('.form-group').removeClass('has-warning')
    $document.duScrollTopAnimated(0)


  $scope.change = (id) ->
    $('#' + id).addClass('has-warning')





    
