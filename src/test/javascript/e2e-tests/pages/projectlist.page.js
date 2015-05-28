'use strict';

var ProjectlistPage = function () {
  browser.get('/projectlist');
};

ProjectlistPage.prototype = Object.create({}, {
  searchInputField: {
    get: function () {
      return element(by.model('projectlist'));
    }
  },
  addProjectButton: {
    get: function () {
      return element(by.id('add-project'));
    }
  },
  lastProject: {
    get: function () {
      return element.all(by.className('project')).last();
    }
  },
  openLastProject: {
    value: function () {
      this.lastProject.element(by.css('fa fa-edit')).click();
      browser.wait(function () {
        return browser.getCurrentUrl().then(function (url) {
          return url.indexOf('/project/') > -1;
        });
      }, 500);
    }
  },
  addProject: {
    value: function () {
      var page = this;
      page.addProjectButton.click().then(function () {
        browser.wait(function () {
        }, 500);
      });
    }
  },
  projectCount: {
	    get: function () {
	      return element(by.id('project-count')).getText().then(function (text) {
	        return parseInt(text);
	      });
	    }
	  }, 
  filteredProjectCount: {
	    get: function () {
	      return element.all(by.className('project')).count();
	    }
	  },
  removeProject: {
		 get: function () {
		    return element.all(by.className('fa fa-times')).last();
		    }
		  },
  searchProject: {
    value: function (searchKeyword) {
      this.searchInputField.clear();
      this.searchInputField.sendKeys(searchKeyword);
    }
  }
});

module.exports = ProjectlistPage;