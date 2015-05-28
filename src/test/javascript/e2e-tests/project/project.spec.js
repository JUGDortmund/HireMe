'use strict';

var SearchPage = require('../pages/projectlist.page.js');
var ProfilePage = require('../pages/project.page');

xdescribe('profile page', function () {
	
	  var searchPage;
	  var profilePage;
	  beforeEach(function () {
	    projectlistPage = new ProjectlistPage();
	    projectlistPage.addProject();
	    projectlistPage.openLastProject();
	    projectPage = new ProjectPage;
	});
	  
	it('should show the title of the selected project', function () {
	  expect(projectPage.title.getAttribute('value')).toBe('new Project');
	});
	
	it('should redirect if a project with a valid id is called', function () {
	  expect(browser.getCurrentUrl()).toContain("/project/");
	});
	
	it('should save the new title of the selected project after save button is pressed', function () {
	  var expectedText = 'newProjectTitle';
	  var title = projectPage.title;
	  title.click();
	  title.clear();
	  title.sendKeys(expectedText);
	  projectPage.save();
	  expect(title.getAttribute('value')).toBe(expectedText);
	});
	
	 it('should reset the new title of the selected project after cancel button is pressed', function () {
		    var newText = 'newProjectTitle';
		    var title = projectPage.title;
		    var oldText = title.getAttribute('value');
			title.click();
			title.clear();
		    title.sendKeys(newText);
		    projectPage.cancel();
		    expect(title.getAttribute('value')).toBe(oldText);
	  });	
	  
	  it('should set a date correctly and persist date to project', function() {
		    var inputDate = '01.03.01';
		    var inputStart= projectPage.start;
		    inputStart.click();
		    inputStart.clear();
		    inputStart.sendKeys(inputDate);
		    projectPage.save();
		    expect(inputStart.getAttribute('value')).toBe(inputDate);
	  });
	  
	  it('should be able to create a new tag', function () {
		    var locations = projectPage.locations;
		    locations.click();
		    locations.sendKeys("Test");
		    locations.sendKeys(protractor.Key.ENTER);
		    expect(projectPage.locationsTagCount).toBe(1);
		  });
	  
});	  