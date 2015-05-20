angular.module 'tags', ['restangular']
.factory 'tagService', (Restangular) ->
  tagService = {
    tags: Restangular.all('tags')
    tagList: []
    loadTags: () ->
      _this = this
      @tags.getList().then (value) ->
        _this.tagList = value
    getTags: () ->
      @tagList
    getTag: (name) ->
      result = _.find @tagList, (x)-> x.name is name
      if result? then result.values else []
  }

  tagService.loadTags()
  return tagService