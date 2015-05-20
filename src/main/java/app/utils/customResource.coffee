###*
# Custom module for construction of resource urls.
#
# Usage of 'toResourceUrl' and 'toThumbnailUrl' filter:
# - Use <img src="{{resourceId|toResourceUrl}}"/> to convert given resourceId to resource url. Example: resourceId=5 => /api/resource/5/original
# - Use <img src="{{resourceId|toThumbnailUrl}}"/> to convert given resourceId to thumbnail url. Example: resourceId=5 => /api/resource/5/thumbnail
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
  path = '/api/resource'
  placeholder = 'http://placehold.it/100x100'
  thumbnail = "thumbnail"
  original = "original"
  
  @setPath = (path) ->
    @path = path
    return
    
  @setPlaceholder = (placeholder) ->
    @placeholder = placeholder
    return
    
  @setThumbnail = (thumbnail) ->
    @thumbnail = thumbnail
    return
    
  @setOriginal = (original) ->
    @original = original
    return
    
  @$get = ->
    {
      get: (id, format) ->
        return if id? and format? then (path + '/' + id + '/' + format) else placeholder
      getOriginal: (id) ->
        return @get(id, original)
      getThumbnail: (id) ->
        return @get(id, thumbnail)
    }
  return
.filter 'toResourceUrl', (customResource) ->
  return (id) ->
    return customResource.getOriginal(id)
.filter 'toThumbnailUrl', (customResource) ->
  return (id) ->
    return customResource.getThumbnail(id)
  