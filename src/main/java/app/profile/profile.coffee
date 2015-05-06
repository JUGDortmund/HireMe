angular.module 'profile', []
.config ($routeProvider) ->
  $routeProvider.when '/profile', templateUrl: '/profile/profile.tpl.html'
  $routeProvider.when '/profile/:profileId',
    templateUrl: '/profile/profile.tpl.html',
    controller: 'ProfileCtrl',
    resolve:
      profile: (Restangular, $route) ->
        Restangular.one('profile', $route.current.params.profileId).get()
.factory 'ProfileService', () ->
  profileService = {}
  profileService.profile = undefined;

  profileService.addProfile = (value) ->
  	@profile = angular.copy(value)
  	console.log @profile

  profileService.getProfile = () ->
    console.log @profile
    return @profile
    

  return profileService
.controller 'ProfileCtrl', ($scope, $timeout, Restangular, profile, ProfileService) ->
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
      "profile.qualifikation",
      "profile.summary"
    ], (newValue, oldValue) ->
      if (newValue != oldValue && $scope.editMode)
       $scope.showme = true
       
  $scope.enableEditMode = ->
  	$scope.editMode = true
  	return
  $scope.save = ->
    profile.put().then (->
      ###successful case###
      $scope.showme = false

    ), ->
      ###error case###
      $scope.error = true;
      $timeout (->
        $scope.error = false

      ), 10000

  $scope.cancel = ->
    $scope.editMode = false
  
    ### set scope to false ### 
    $scope.profile = angular.copy($scope.originProfile)
    $scope.showme = false
    return
    
  return  
    
