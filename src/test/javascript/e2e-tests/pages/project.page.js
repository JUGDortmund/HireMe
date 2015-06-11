'use strict';

var ProjectPage = function () {
};

ProjectPage.prototype = Object.create({}, {
  title: {
    get: function () {
      return element(by.id('title'));
    }
  },
  start: {
    get: function () {
      return element(by.id('start'));
    }
  },
  locationsTagCount: {
    get: function () {
      return element.all(by.css('#locations ti-tag-item')).count();
    }
  },
  locations: {
    get: function () {
      return element(by.css('#locations input[type="text"]'));
    }
  },
  save: {
    value: function () {
      element(by.id('save-button')).click();
    }
  },
  cancel: {
    value: function () {
      element(by.id('cancel-button')).click();
    }
  },
  msgSuccess : {
    get: function () {
      return element(by.id('project-msg-success'));
    }
  },
  msgError : {
    get: function () {
      return element(by.id('project-msg-error'));
    }
  }  
});

module.exports = ProjectPage;