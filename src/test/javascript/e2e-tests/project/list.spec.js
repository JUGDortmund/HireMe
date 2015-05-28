'use strict';
var ProjectListPage = require('../pages/projectlist.page.js');
var SearchPage = require('../pages/search.page.js');
var ProfilePage = require('../pages/profile.page.js');


describe('projectList page', function() {

  var projectListPage;

  beforeEach(function() {
    projectListPage = new ProjectListPage();
    projectListPage.addProject();
    projectListPage.addProject();
  });

  it('should show all projects if search keyword is empty', function() {
    projectListPage.searchProject("");
    expect(element(by.id('search-results-none')).isDisplayed()).toBeFalsy();
    var projectCount = projectListPage.projectCount;
    expect(projectListPage.filteredProjectCount).toBe(projectCount);
  });
  
  it('should not show any project if search keyword does not match', function() {
    projectListPage.searchProject("LONG_STRING_HOPEFULLY_NOT_MATCHING_ANY_PROJECT");
    expect(element(by.id('search-results-none')).isDisplayed()).toBeTruthy();
    expect(projectListPage.filteredProjectCount).toBe(0);
  });
  
  it('should show profiles containing \"New Project\" if search keyword is \"New Project\"', function() {
    projectListPage.searchProject("New Project");
    expect(element(by.id('search-results-none')).isDisplayed()).toBeFalsy();
    var projectCount = projectListPage.projectCount;
    expect(ProjectListPage.filteredProjectCount).toBe(projectCount);
    
    projectListPage.searchProject("Test");
    expect(element(by.id('search-results-none')).isDisplayed()).toBeFalsy();
    expect(projectListPage.filteredProjectCount).toBe(0);
  });
  
  it('should search for \"blub\" if search page was called with request parameter \"/search?q=blub\"', function() {
    browser.get('/search?q=blub');
    expect(projectListPage.searchInputField.getAttribute('value')).toBe('blub');
  });
  
  it('should remove a project', function() {
	    var projectCount = projectListPage.projectCount;
	    projectListPage.remove.click();
	    expect(projectListPage.projectCount).toBe(projectCount-1);
	  });
  
  it('should not remove a refereced project', function() {
	    
	  	var projectCount = projectListPage.projectCount;
	    var searchPage = new SearchPage();
	    searchPage.addProfile();
	    searchPage.openLastProfile();
	    var profilePage = new ProfilePage;
	    profilePage.addProjectAssociation();
	    profilePage.save();
	    projectListPage = new ProjectListPage();
	    projectListPage.removeProject().click();
	    expect(projectListPage.projectCount).toBe(projectCount);
	  });
  
  it('should add a new project', function() {
	    var projectCount = projectListPage.projectCount;
	    projectListPage.addProject().click();
	    expect(projectListPage.projectCount).toBe(projectCount+1);
	  });
  
  

  
});
