'use strict';

var ProjectPage = function () {
};

ProjectPage.prototype = Object.create({}, {
  title: {
    get: function () {
      return element.all(by.id('title')).last();
    }
  },
  start: {
    get: function () {
      return element(by.id('start'));
    }
  },
  end: {
	    get: function () {
	      return element(by.id('end'));
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
  technologies: {
	    get: function () {
	      return element(by.css('#technologies input[type="text"]'));
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
  },
  reject: {
	value: function () {
		  element(by.id('reject-button')).click();
		}
	  },
  dashboard: {
	value: function () {
	   element(by.id('dashboard')).click();
	}
   }
});

module.exports = ProjectPage;