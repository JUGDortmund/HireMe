angular.module('projectList', ['utils.autofocus', 'duScroll'])
.value('duScrollDuration', 500)
.value('duScrollOffset', 30)
.config ($routeProvider) ->
  $routeProvider.when '/projectlist',
    templateUrl: '/project/list.tpl.html',
    controller: 'ProjectListCtrl',
    resolve:
      projects: (Restangular) -> Restangular.all('project').getList()
.controller 'ProjectListCtrl', ($scope, $timeout, $location, $window, Restangular, projects, $document, $parse, $rootScope, routeService) ->
  $scope.projects = projects
  getProjects = -> projects.getList().then (projects) ->
    $scope.projects = projects
  console.log routeService.getData()

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

  $scope.add = ->
    Restangular.one('project').post().then (createdProject)->
      $window.location.href = '/project/' + createdProject.id
      getProjects()
    return

  $scope.remove = (id) ->
    Restangular.one('project', id).remove().then (->
      getProjects()
      showMessage('success')
      return
    ), (response) ->
      $scope.profiles = response.data
      if response.status == 450
        showMessage('errorReferenceProfiles')
      else
        showMessage('error')
      return
      
  $rootScope.$on '$locationChangeSuccess', (event, newUrl, oldUrl)->
    routeService.setData(oldUrl)
    return