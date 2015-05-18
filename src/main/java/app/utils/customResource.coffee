###*
# Custom module for construction of resource urls.
#
# Usage of 'toResourceUrl' filter:
# - Use <img src="{{resourceId|toResourceUrl}}"/> to convert given resourceId to resource url. Example: resourceId=5 => /api/resource/5
# - If given resourceId is empty, outputs url to placeholder image instead
# 
# Usage of 'customResource' provider
# - Inject and configure customResource provider to globally configure the resource url path and the placeholder image:
#  angular.module('myApp').config(function(customResource) {
#    customResource.setPath("/my/resource/path");
#    setPlaceholder.setPlaceholder("http://my.domain.com/placeholder.jpg");
#  });  
#  
###

angular.module 'utils.customResource', []
.provider 'customResource', ->
  path = '/api/resource/'
  placeholder = 'http://placehold.it/100x100'
  
  @setPath = (path) ->
    @path = path
    return
    
  @setPlaceholder = (placeholder) ->
    @placeholder = placeholder
    return
  
  @$get = ->
    {
      get: (id) ->
        return if id? then path + id else placeholder
    }
  return
.filter 'toResourceUrl', (customResource) ->
  return (id) ->
    return customResource.get(id)
  