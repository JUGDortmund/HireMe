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
  getLastLocationText: {
	get: function () {
	  return element.all(by.css('#locations .tag-item span')).last().getText();
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
  }
});

module.exports = ProjectPage;