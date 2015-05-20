angular.module( 'project', ['duScroll'])
.value('duScrollDuration', 500)
.value('duScrollOffset', 30)
.config ($routeProvider) ->
  $routeProvider.when '/project', templateUrl: '/project/project.tpl.html'
  $routeProvider.when '/project/:projectId',
    templateUrl: '/project/project.tpl.html',
    controller: 'ProjectCtrl',
    resolve:
      project: (Restangular, $route) ->
        Restangular.one('profile', $route.current.params.profileId).get()
.controller 'ProjectCtrl', ($scope, $timeout, Restangular, project, $document, $parse) ->
  dateFormat = $('.datepicker').attr("data-date-format").toUpperCase()
  $scope.project = project        