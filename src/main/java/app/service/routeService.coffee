angular.module 'route', ['restangular']
.factory 'routeService', ->
  history = ''
  {
    getData: ->
      return history
    setData: (newHistory) ->
      history = newHistory
      return
    resetData: ->
      history = ''
      return
  }

