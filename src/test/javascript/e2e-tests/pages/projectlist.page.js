'use strict';

var ProjectListPage = function () {
  browser.get('/projectlist');
};

ProjectListPage.prototype = Object.create({}, {
  searchInputField: {
    get: function () {
      return element(by.model('projectList'));
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
		       return page.projectCount.then(function (count) {
		        return count > 0;
		     });
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

module.exports = ProjectListPage;