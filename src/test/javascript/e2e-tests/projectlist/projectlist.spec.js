'use strict';
var ProjectlistPage = require('../pages/projectlist.page.js');
var SearchPage = require('../pages/search.page.js');
var ProfilePage = require('../pages/profile.page.js');


describe('projectlist page', function() {

  var projectlistPage;

  beforeEach(function() {
    ProjectlistPage = new projectlistPage();
    ProjectlistPage.addProject();
    ProjectlistPage.addProject();
  });

  it('should show all projects if search keyword is empty', function() {
    ProjectlistPage.searchProject("");
    expect(element(by.id('search-results-none')).isDisplayed()).toBeFalsy();
    expect(ProjectlistPage.filteredProjectCount).toBe(2);
    expect(ProjectlistPage.projectCount).toBe(2);
  });
  
  it('should not show any project if search keyword does not match', function() {
    ProjectlistPage.searchProject("LONG_STRING_HOPEFULLY_NOT_MATCHING_ANY_PROJECT");
    expect(element(by.id('search-results-none')).isDisplayed()).toBeTruthy();
    expect(ProjectlistPage.filteredProjectCount).toBe(0);
    expect(ProjectlistPage.projectCount).toBe(2);
  });
  
  it('should show profiles containing \"New Project\" if search keyword is \"New Project\"', function() {
    ProjectlistPage.searchProject("New Project");
    expect(element(by.id('search-results-none')).isDisplayed()).toBeFalsy();
    expect(ProjectlistPage.filteredProjectCount).toBe(2);
    expect(ProjectlistPage.projectCount).toBe(2);
    
    projectlistPage.searchProject("Test");
    expect(element(by.id('search-results-none')).isDisplayed()).toBeFalsy();
    expect(projectlistPage.filteredProjectCount).toBe(0);
    expect(projectlistPage.projectCount).toBe(2);
  });
  
  it('should search for \"blub\" if search page was called with request parameter \"/search?q=blub\"', function() {
    browser.get('/search?q=blub');
    expect(ProjectlistPage.searchInputField.getAttribute('value')).toBe('blub');
  });
  
  it('should remove a project', function() {
	    ProjectlistPage.remove.click();
	    expect(ProjectlistPage.projectCount).toBe(1);
	  });
  
  it('should not remove a refereced project', function() {
	    
	    ProjectlistPage.removeProject().click();
	    searchPage = new SearchPage();
	    searchPage.addProfile();
	    searchPage.openLastProfile();
	    profilePage = new ProfilePage;
	    profilePage.addProjectAssociation();
	    profilePage.save();
	    projectlistPage = new ProjectlistPage();
	    ProjectlistPage.removeProject().click();
	    expect(ProjectlistPage.projectCount).toBe(1);
	  });
  
  it('should add a new project', function() {
	    ProjectlistPage.addProject().click();
	    expect(ProjectlistPage.projectCount).toBe(3);
	  });
  
  

  
});
