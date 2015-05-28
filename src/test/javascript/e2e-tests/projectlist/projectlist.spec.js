'use strict';
var SearchPage = require('../pages/projectlist.page.js');

describe('projectlist page', function() {

  var projectlistPage;

  beforeEach(function() {
    projectlistPage = new projectlistPage();
    projectlistPage.addProject();
    projectlistPage.addProject();
  });

  it('should show all projects if search keyword is empty', function() {
    projectlistPage.searchProject("");
    expect(element(by.id('search-results-none')).isDisplayed()).toBeFalsy();
    expect(projectlistPage.filteredProjectCount).toBe(2);
    expect(projectlistPage.projectCount).toBe(2);
  });
  
  it('should not show any project if search keyword does not match', function() {
    projectlistPage.searchProject("LONG_STRING_HOPEFULLY_NOT_MATCHING_ANY_PROJECT");
    expect(element(by.id('search-results-none')).isDisplayed()).toBeTruthy();
    expect(projectlistPage.filteredProjectCount).toBe(0);
    expect(projectlistPage.projectCount).toBe(2);
  });
  
  it('should show profiles containing \"New Project\" if search keyword is \"New Project\"', function() {
    projectlistPage.searchProject("New Project");
    expect(element(by.id('search-results-none')).isDisplayed()).toBeFalsy();
    expect(projectlistPage.filteredProjectCount).toBe(2);
    expect(projectlistPage.projectCount).toBe(2);
    
    projectlistPage.searchProject("Test");
    expect(element(by.id('search-results-none')).isDisplayed()).toBeFalsy();
    expect(projectlistPage.filteredProjectCount).toBe(0);
    expect(projectlistPage.projectCount).toBe(2);
  });
  
  it('should search for \"blub\" if search page was called with request parameter \"/search?q=blub\"', function() {
    browser.get('/search?q=blub');
    expect(projectlistPage.searchInputField.getAttribute('value')).toBe('blub');
  });
  
  it('shuold remove a project', function() {
	    projectlistPage.remove.click();
	    expect(projectlistPage.projectCount).toBe(1);
	  });
  
});
