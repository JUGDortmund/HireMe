angular.module('app', [
    'ngRoute',
    'dashboard',
    'profile',
    'search'
])
    .config(function ($locationProvider) {
                $locationProvider.html5Mode({
                    enabled: true,
                    requireBase: false
                });
            })
  .controller('NavigationCtrl', function ($scope, $location, $http) {
                $http.get('/api/getGitProperties').
                  success(function (data, status, headers, config) {
                            $scope.gitPropertyDTO = data.gitPropertyDTO;
                          }).
                  error(function (data, status, headers, config) {
                          console.log("ERROR");
                        });
                $scope.isActive = function (route) {
                        return route === $location.path();
                    };
                $scope.triggerBuildInformation = function () {
                  var liBuildInformation = angular.element(document.querySelector("#li-build-information"));
                  liBuildInformation.toggleClass("active");
                }
                })
    .controller('HeaderCtrl', function ($scope) {
                    $scope.init = function () {
                        $.AdminLTE.pushMenu("[data-toggle='offcanvas']");
                    };
                });
