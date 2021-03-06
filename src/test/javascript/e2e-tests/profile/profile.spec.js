'use strict';

var pathUtil = require('path');

var SearchPage = require('../pages/search.page.js');
var ProfilePage = require('../pages/profile.page.js');
var ProjectListPage = require('../pages/projectlist.page.js');
var ProjectPage = require('../pages/project.page.js');

require('../../conf/capabilities.js');

describe('profile page', function () {

  var searchPage;
  var profilePage;
  var projectListPage;
  var projectPage;
  beforeEach(function () {
    searchPage = new SearchPage();
    searchPage.addProfile();
    searchPage.openLastProfile();
    profilePage = new ProfilePage;
  });

  it('should show the name of the selected user', function () {
    expect(profilePage.firstName.getAttribute('value')).toBe('Max');
  });

  it('should redirect if a profile with a valid id is called', function () {
    expect(browser.getCurrentUrl()).toContain("/profile/");
  });

  it('should save the new name of the selected user after save button is pressed', function () {
    var expectedText = 'newUserFirstName';
    var firstname = profilePage.firstName;
    firstname.click();
    firstname.clear();
    firstname.sendKeys(expectedText);
    profilePage.save();
    expect(firstname.getAttribute('value')).toBe(expectedText);
  });

  it('should reset the new name of the selected user after cancel button is pressed and reject button', function () {
    var newText = 'newUserFirstName';
    var firstname = profilePage.firstName;
    var oldText = firstname.getAttribute('value');
    firstname.click();
    firstname.clear();
    firstname.sendKeys(newText);
    profilePage.cancel();
    expect(firstname.getAttribute('value')).toBe(newText);
    profilePage.reject();
    expect(firstname.getAttribute('value')).toBe(oldText);  
  });
  
  it('should not leave profile after change until submit reject button', function () {
	    var newText = 'newUserFirstName';
	    var firstname = profilePage.firstName;
	    var oldText = firstname.getAttribute('value');
	    firstname.click();
	    firstname.clear();
	    firstname.sendKeys(newText);
	    profilePage.dashboard();  
	    expect(browser.getCurrentUrl()).toContain("/profile/");
	    profilePage.reject();
	    expect(firstname.getAttribute('value')).toBe(oldText);  
	  });

  it('should set a date correctly and persist date to profile', function () {
    var inputWorkExperience = profilePage.workExperience;
    inputWorkExperience.click();
    var button = element.all(by.buttonText('akt. Monat')).first();
    button.click();  
    profilePage.save();
    expect(inputWorkExperience.getAttribute('value')).toBe(getTodaysDate());
  });

  it('should be able to create a new tag', function () {
    var degrees = profilePage.degrees;
    degrees.click();
    degrees.sendKeys("Test");
    degrees.sendKeys(protractor.Key.ENTER);
    expect(profilePage.degreeTagCount).toBe(1);
  });
  
  it('should be able to create a new tag with length one', function () {
	    var degrees = profilePage.degrees;
	    degrees.click();
	    degrees.sendKeys("T");
	    degrees.sendKeys(protractor.Key.ENTER);
	    expect(profilePage.degreeTagCount).toBe(1);
	  });

  it('should be able to remove an existing tag', function () {
    var degrees = profilePage.degrees;
    degrees.click();
    degrees.sendKeys("Test");
    degrees.sendKeys(protractor.Key.ENTER);

    degrees.click();
    degrees.sendKeys("Test2");
    degrees.sendKeys(protractor.Key.ENTER);

    degrees.click();
    degrees.sendKeys("Test3");
    degrees.sendKeys(protractor.Key.ENTER);

    degrees.sendKeys(protractor.Key.BACK_SPACE);
    degrees.sendKeys(protractor.Key.BACK_SPACE);
    profilePage.save();
    expect(profilePage.degreeTagCount).toBe(2);
  });

  it('should provide suggestions for tag fields from other profiles', function () {
    var degrees = profilePage.degrees;
    degrees.click();
    degrees.sendKeys("OtherProfileTag1");
    degrees.sendKeys(protractor.Key.ENTER);
    degrees.sendKeys("OtherProfileTag2");
    degrees.sendKeys(protractor.Key.ENTER);

    profilePage.save();

    searchPage = new SearchPage();
    searchPage.addProfile();
    searchPage.openLastProfile();

    degrees = profilePage.degrees;
    degrees.click();
    degrees.sendKeys("OtherProfileTag");

    var suggestionsCount = element.all(by.css('.suggestion-item')).count();
    expect(suggestionsCount).toBe(2);
  });

  it('should be able to add a project association', function () {
    var oldProjectAssociationCount = profilePage.projectAssociationCount;
    profilePage.addProjectAssociation();
    expect(profilePage.projectAssociationCount).toBeGreaterThan(oldProjectAssociationCount);
  });

  it("should save changes inside the project associations", function () {
    profilePage.addProjectAssociation();
    var startField = element(by.id('start-0'));
    startField.click();
    var button = element.all(by.buttonText('akt. Monat')).get(1);
    button.click();  
    profilePage.save();
    expect(startField.getAttribute('value')).toContain(getTodaysDate());
  });

  it('should be able to delete a project association', function () {
	  browser.driver.manage().window().setSize(800, 600);
	  profilePage.addProjectAssociation();
	  var startField = element(by.id('start-0'));
	  startField.click();
	  var button = element.all(by.buttonText('akt. Monat')).get(1);
	  button.click();  
	  profilePage.deleteProjectAssociation();
	  expect(profilePage.projectAssociationCount).toEqual(0);
  });

  it('should be able to delete a project association, after it was saved persistent', function () {
	  profilePage.addProjectAssociation();
	  var startField = element(by.id('start-0'));
	  startField.click();
	  var button = element.all(by.buttonText('akt. Monat')).get(1);
	  button.click();
	  profilePage.save();
	  profilePage.deleteProjectAssociation();
	  profilePage.save();
	  expect(profilePage.projectAssociationCount).toEqual(0);
  });

  it('project associations should not be deleted, when cancel and reject button button are pressed', function () {
	  profilePage.addProjectAssociation();
	  var startField = element(by.id('start-0'));
	  startField.click();
	  var button = element.all(by.buttonText('akt. Monat')).get(1);
	  button.click();
	  profilePage.save();
	  profilePage.deleteProjectAssociation();
	  profilePage.cancel();
	  expect(profilePage.projectAssociationCount).toEqual(0)
	  profilePage.reject();
	  expect(profilePage.projectAssociationCount).toEqual(1)
  });
  
  it('should successfully upload a profile picture and set it after save', function () {
    var oldThumbnailPath = profilePage.thumbnailPath;
    profilePage.uploadImage(getFileName());
    profilePage.save();
    expect(profilePage.thumbnailPath).not.toBe(oldThumbnailPath);
  });

  it('should revert a profile picture change on cancel', function () {
    var oldThumbnailPath = profilePage.thumbnailPath;
    profilePage.uploadImage(getFileName());
    profilePage.cancel();
    expect(profilePage.thumbnailPath).not.toBe(oldThumbnailPath);
    profilePage.reject();
    expect(profilePage.thumbnailPath).toBe(oldThumbnailPath);
  });
  
  it('should load default values from project if field are empty', function() {
	  var startField = element(by.id('start-0'));
	  var endField = element(by.id('end-0'));
	  var locationsField = element(by.id('projectAssociations-locations-0'));
	  var technologiesField = element(by.id('projectAssociations-technologies-0'));
	  buildProjectStructure("TestLocation", "TestTechnologies");
	  profilePage.addProjectAssociation();
	  profilePage.selectLastProjectInLastProjectAssociation();
	  expect(startField.getAttribute('value')).toContain(getTodaysDate());
	  expect(endField.getAttribute('value')).toContain(getTodaysDate());
	  expect(profilePage.getLastLocationText).toBe("TestLocation");
	  expect(profilePage.getLastTechnologieText).toBe("TestTechnologies");	  
  });
  it('should display default values from project below if field are empty', function() {
	  var startField = element(by.id('start-0'));
	  var endField = element(by.id('end-0'));
	  var locationsField = element(by.id('projectAssociations-locations-0'));
	  var technologiesField = element(by.id('projectAssociations-technologies-0'));
	  buildProjectStructure("TestLocation", "TestTechnologies");
	  profilePage.addProjectAssociation();
	  profilePage.selectLastProjectInLastProjectAssociation();
	  expect(startField.getAttribute('value')).toContain(getTodaysDate());
	  expect(endField.getAttribute('value')).toContain(getTodaysDate());
	  expect(profilePage.getLastLocationText).toBe("TestLocation");
	  expect(profilePage.getLastTechnologieText).toBe("TestTechnologies");	  
  });
  
  it('should not load default values from project if field are full', function() {
	  var startField = element(by.id('start-0'));
	  var endField = element(by.id('end-0'));
	  var locationsField = element(by.id('projectAssociations-locations-0'));
	  var technologiesField = element(by.id('projectAssociations-technologies-0'));
	  buildProjectStructure("TestLocation", "TestTechnologies");
	  profilePage.addProjectAssociation();
	  profilePage.selectLastProjectInLastProjectAssociation();
	  profilePage.save();
	  expect(startField.getAttribute('value')).toContain(getTodaysDate());
	  expect(endField.getAttribute('value')).toContain(getTodaysDate());
	  expect(profilePage.getLastLocationText).toBe("TestLocation");
	  expect(profilePage.getLastTechnologieText).toBe("TestTechnologies");	  
  });
  
  it('should able to download anonym and standrad pdf ', function(){
		 profilePage.download(); 
		 expect(profilePage.getTemplate('Anonym')).toBe("Anonym");
		 expect(profilePage.getTemplate('Standard')).toBe("Standard");
  });
	  
  
  function getTodaysDate() {
	  var date = new Date();
	  var month = date.getMonth() + 1 ;
	  if(month< 10) {
		  month = "0"+month
	  }
	  var newdate = month + "." + date.getFullYear();
	  return newdate;
  }

  function buildProjectStructure(location, technologie) {
	  projectListPage = new ProjectListPage();
	  projectListPage.addProjectAndReturnToProjectList();
	  projectListPage.openLastProject();
	  projectPage = new ProjectPage();
	  var locations = projectPage.locations;
	  locations.click();
	  locations.sendKeys(location);
	  locations.sendKeys(protractor.Key.ENTER);
	  var techonologies = projectPage.technologies;
	  techonologies.click();
	  techonologies.sendKeys(technologie);
	  techonologies.sendKeys(protractor.Key.ENTER);
	  var input = projectPage.start;
	  input.click();
	  var button = element.all(by.buttonText('akt. Monat')).get(0);
	  button.click();
	  input = projectPage.end;
	  input.click();
	  button = element.all(by.buttonText('akt. Monat')).get(1);
	  button.click();
	  projectPage.save();
	  searchPage = new SearchPage();
	  searchPage.openLastProfile();
	  profilePage = new ProfilePage;
  }
  
  function getFileName() {
    if (browser.inWindows()) {
      return "C:\\Users\\Public\\Pictures\\Sample Pictures\\flagge.gif";
    } else {
      var defaultImage = "../../../resources/fileupload.png";
      return pathUtil.resolve(__dirname, defaultImage);
    }
  }
  
});
