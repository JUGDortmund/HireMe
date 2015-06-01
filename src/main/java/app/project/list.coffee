angular.module('projectList', ['duScroll'])
.value('duScrollDuration', 500)
.value('duScrollOffset', 30)
.config ($routeProvider) ->
  $routeProvider.when '/projectlist',
    templateUrl: '/project/list.tpl.html',
    controller: 'ProjectListCtrl',
    resolve:
      projects: (Restangular) -> Restangular.all('project').getList()
.controller 'ProjectListCtrl', ($scope, $timeout, Restangular, projects, $document, $parse, tagService) ->
  $scope.projects = projects
  getProjects = -> projects.getList().then (projects) ->
    $scope.projects = projects

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
    Restangular.one('project').post().then ->
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