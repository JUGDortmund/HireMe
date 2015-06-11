'use strict';

var ProjectListPage = require('../pages/projectlist.page.js');
var ProjectPage = require('../pages/project.page.js');
var SearchPage = require('../pages/search.page.js');

describe('project page', function () {
	
	  var projectListPage;
	  var projectPage;
	  
	  
	  beforeEach(function () {
			projectListPage = new ProjectListPage();
	    projectListPage.addProject();
	    projectListPage.openLastProject();
	    projectPage = new ProjectPage();
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
		    var inputStart = projectPage.start;
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
	  
	  it('should be able to create a new tag with length one', function () {
		    var locations = projectPage.locations;
		    locations.click();
		    locations.sendKeys("T");
		    locations.sendKeys(protractor.Key.ENTER);
		    expect(projectPage.locationsTagCount).toBe(1);
		  });
	  
	  it('should be able to create a new tag without replace spaces with dashes', function () {
		    var locations = projectPage.locations;
		    var inputLocation = 'Test without dashes';
		    locations.click();
		    locations.sendKeys(inputLocation);
		    locations.sendKeys(protractor.Key.ENTER);
		    expect(projectPage.getLastLocationText).toBe(inputLocation);
		    expect(projectPage.locationsTagCount).toBe(1);
		  });
	  
	  it('should set an invalid date and persist it after correcting it', function() {
		    var incorrectInputDate = '01.0x.01';
		    var inputStart = projectPage.start;
		    inputStart.click();
		    inputStart.clear();
		    inputStart.sendKeys(incorrectInputDate);
		    inputStart.sendKeys(protractor.Key.DOWN);
		    inputStart.sendKeys(protractor.Key.ENTER);
		    inputStart.click();
		    var newValue = inputStart.getAttribute('value');
		    projectPage.save();
		    expect(inputStart.getAttribute('value')).toBe(newValue);
		  });
	  
	  it('should be able to remove an existing tag', function () {
		    var locations = projectPage.locations;
		    locations.click();
		    locations.sendKeys("Test");
		    locations.sendKeys(protractor.Key.ENTER);

		    locations.click();
		    locations.sendKeys("Test2");
		    locations.sendKeys(protractor.Key.ENTER);

		    locations.click();
		    locations.sendKeys("Test3");
		    locations.sendKeys(protractor.Key.ENTER);

		    locations.sendKeys(protractor.Key.BACK_SPACE);
		    locations.sendKeys(protractor.Key.BACK_SPACE);
		    projectPage.save();
		    expect(projectPage.locationsTagCount).toBe(2);
		  });
	  
	  it('should provide suggestions for tag fields from other profiles', function () {
		    var locations = projectPage.locations;
		    locations.click();
		    locations.sendKeys("Test123");
		    locations.sendKeys("Test124");
		    locations.sendKeys(protractor.Key.ENTER);

		    projectPage.save();

			var newProjectListPage = new ProjectListPage();
			newProjectListPage.addProject();
			newProjectListPage.openLastProject();

		    locations = projectPage.locations;
		    locations.click();
		    locations.sendKeys("Test");

		    var suggestionsCount = element.all(by.css('.suggestion-item')).count();
		    expect(suggestionsCount).toBe(3);
		  });
	  
	  it('should save project if start date is edited, cancel button is pressed, date edited again, and save button is pressed', function () {
	    var startField = projectPage.start
	    startField.click();
	    startField.clear();
	    startField.sendKeys("01.01.1000");
	    projectPage.save();
	    
	    startField.click();
	    startField.clear();
	    startField.sendKeys("01.01.2000");
	    projectPage.cancel();
	    
	    startField.click();
	    startField.clear();
	    startField.sendKeys("01.01.3000");
	    projectPage.save();

	    expect(projectPage.msgSuccess.isDisplayed()).toBeTruthy();
	    expect(projectPage.msgError.isDisplayed()).toBeFalsy();
	  });
});	  