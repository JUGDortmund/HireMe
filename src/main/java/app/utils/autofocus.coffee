### global angular ###

###*
# https://gist.github.com/mlynch/dd407b93ed288d499778
#
# the HTML5 autofocus property can be finicky when it comes to dynamically
# loaded templates and such with AngularJS. Use this simple directive to tame
# this beast once and for all.
#
# Usage:
# <input type="text" autofocus>
###

do ->
  'use strict'
  angular.module('utils.autofocus', []).directive 'autofocus',
    ($timeout) ->
      {
        restrict: 'A'
        link: ($scope, $element) ->
          $timeout ->
            $element[0].focus = ->
              this.select();
              return;
            $element[0].focus()
            return
          return
      }
  return