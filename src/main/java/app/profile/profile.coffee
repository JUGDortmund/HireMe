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
    this.profile = value;

  profileService.getProfile = () ->
    return this.profile

  return profileService
.controller 'ProfileCtrl', ($scope, $timeout, Restangular, profile, ProfileService) ->
  $scope.profile = profile

  ProfileService.addProfile(profile)

  $scope.$watchGroup [
      "profile.firstname",
      "profile.lastname",
      "profile.degree",
      "profile.careerStage",
      "profile.workExperience",
      "profile.languages"
    ], (newValue, oldValue) ->
      if (newValue != oldValue)
       $scope.showme = true
       
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
    ### set scope to false ###
    $scope.showme = false
  



