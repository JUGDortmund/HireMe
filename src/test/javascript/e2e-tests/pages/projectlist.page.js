'use strict';

var ProjectListPage = function () {
  browser.get('/projectlist');
};

ProjectListPage.prototype = Object.create({}, {
  searchInputField: {
    get: function () {
      return element(by.model('search'));
    }
  },
  addProjectButton: {
    get: function () {
      return element(by.id('add-project'));
    }
  },
  addProjectButtonUnder: {
	    get: function () {
	      return element(by.id('add-project-under'));
	    }
	  },
  lastProject: {
    get: function () {
      return element.all(by.className('project')).last();
    }
  },
  openLastProject: {
    value: function () {
      this.lastProject.element(by.css('.fa.fa-edit')).click();
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
      page.addProjectButton.click();
      browser.wait(function () {
    	  return browser.executeScript('return !!window.angular');
        }, 1000);
    }
  },
  addProjectAndReturnToProjectList: {
    value: function () {
      var page = this;
      page.addProjectButton.click().then(function () {
        browser.get('/projectlist');
      });
    }
  },
  
  addProjectUnderAndReturnToProjectList: {
	    value: function () {
	      var page = this;
	      page.addProjectButtonUnder.click().then(function () {
	        browser.get('/projectlist');
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
  removeLastProject: {
    value: function () {
      return element.all(by.css('.fa.fa-times')).last().click();
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