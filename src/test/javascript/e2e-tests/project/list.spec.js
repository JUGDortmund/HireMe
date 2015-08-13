'use strict';
var ProjectListPage = require('../pages/projectlist.page.js');
var SearchPage = require('../pages/search.page.js');
var ProfilePage = require('../pages/profile.page.js');
var ProjectPage = require('../pages/project.page.js');

describe('projectList page', function () {

  var projectListPage;
  var projectPage;

  beforeEach(function () {
    projectListPage = new ProjectListPage();
    projectListPage.addProjectAndReturnToProjectList();
  });
  
  it('should redirect to the project edit page after creation of a new project', function () {
	projectListPage.addProject();
	projectPage = new ProjectPage();
	browser.sleep(1000);
    expect(browser.getCurrentUrl()).toContain('/project/');
  });

  it('should show all projects if search keyword is empty', function () {
    projectListPage.searchProject("");
    var projectCount = projectListPage.projectCount;
    expect(projectListPage.filteredProjectCount).toBe(projectCount);
  });

  it('should not show any project if search keyword does not match', function () {
    projectListPage.searchProject("LONG_STRING_HOPEFULLY_NOT_MATCHING_ANY_PROJECT");
    expect(projectListPage.filteredProjectCount).toBe(0);
  });

  it('should show profiles containing \"neues Projekt\" if search keyword is \"neues Projekt\"', function () {
    projectListPage.searchProject("neues Projekt");
    var projectCount = projectListPage.projectCount;
    expect(projectListPage.filteredProjectCount).toBe(projectCount);
  });

  it('should remove a project if \"project remove button\" is pressed', function () {
    var projectCount = projectListPage.projectCount;
    projectListPage.removeLastProject();
    expect(projectListPage.projectCount).toBeLessThan(projectCount);
  });

  it('should not remove a referenced project if \"project remove button\" is pressed', function () {
    var oldProjectCount = projectListPage.projectCount;
    var searchPage = new SearchPage();
    searchPage.addProfile();
    searchPage.openLastProfile();
    var profilePage = new ProfilePage;
    profilePage.addProjectAssociation();
    profilePage.selectLastProjectInLastProjectAssociation();
    profilePage.save();
    projectListPage = new ProjectListPage();
    projectListPage.removeLastProject();
    expect(projectListPage.projectCount).toBe(oldProjectCount);
  });

  it('should add a new project if \"add project button\" is pressed', function () {
    var projectCount = projectListPage.projectCount;
    projectListPage.addProjectAndReturnToProjectList();
    expect(projectListPage.projectCount).toBeGreaterThan(projectCount);
  });
  
  it('should add a new project if \"add project button\" is pressed', function () {
	    var projectCount = projectListPage.projectCount;
	    projectListPage.addProjectUnderAndReturnToProjectList();
	    expect(projectListPage.projectCount).toBeGreaterThan(projectCount);
  });

});
