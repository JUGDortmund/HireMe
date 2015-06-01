'use strict';
var ProjectListPage = require('../pages/projectlist.page.js');
var SearchPage = require('../pages/search.page.js');
var ProfilePage = require('../pages/profile.page.js');

describe('projectList page', function () {

  var projectListPage;

  beforeEach(function () {
    projectListPage = new ProjectListPage();
    projectListPage.addProject();
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

  it('should show profiles containing \"New Project\" if search keyword is \"New Project\"', function () {
    projectListPage.searchProject("New Project");
    var projectCount = projectListPage.projectCount;
    expect(projectListPage.filteredProjectCount).toBe(projectCount);
  });

  it('should search for \"blub\" if search page was called with request parameter \"/search?q=blub\"', function () {
    browser.get('/search?q=blub');
    expect(projectListPage.searchInputField.getAttribute('value')).toBe('blub');
  });

  it('should remove a project', function () {
    var projectCount = projectListPage.projectCount;
    projectListPage.removeLastProject();
    expect(projectListPage.projectCount).toBeLessThan(projectCount);
  });

  it('should not remove a referenced project', function () {

    var projectCount = projectListPage.projectCount;
    var searchPage = new SearchPage();
    searchPage.addProfile();
    searchPage.openLastProfile();
    var profilePage = new ProfilePage;
    profilePage.addProjectAssociation();
    profilePage.selectLastProjectInLastProjectAssociation();
    profilePage.save();
    projectListPage = new ProjectListPage();
    projectListPage.removeLastProject();
    expect(projectListPage.projectCount).toBe(projectCount);
  });

  it('should add a new project', function () {
    var projectCount = projectListPage.projectCount;
    projectListPage.addProject();
    expect(projectListPage.projectCount).toBeGreaterThan(projectCount);
  });

});
