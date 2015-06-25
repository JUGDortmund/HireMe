'use strict';

var pathUtil = require('path');

var SearchPage = require('../pages/search.page.js');
var ProfilePage = require('../pages/profile.page.js');

require('../../conf/capabilities.js');

describe('profile page', function () {

  var searchPage;
  var profilePage;
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
    var inputDate = '01.03.01';
    var inputWorkExperience = profilePage.workExperience;
    inputWorkExperience.click();
    inputWorkExperience.clear();
    inputWorkExperience.sendKeys(inputDate);
    profilePage.save();
    expect(inputWorkExperience.getAttribute('value')).toBe(inputDate);
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


  it('should set an invalid date and persist it after correcting it', function () {
    var incorrectInputDate = '01.0x.01';
    var inputWorkExperience = profilePage.workExperience;
    inputWorkExperience.click();
    inputWorkExperience.clear();
    inputWorkExperience.sendKeys(incorrectInputDate);
    inputWorkExperience.sendKeys(protractor.Key.DOWN);
    inputWorkExperience.sendKeys(protractor.Key.ENTER);
    inputWorkExperience.click();
    var newValue = inputWorkExperience.getAttribute('value');
    profilePage.save();
    expect(inputWorkExperience.getAttribute('value')).toBe(newValue);
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
    degrees.sendKeys("Test123");
    degrees.sendKeys("Test124");
    degrees.sendKeys(protractor.Key.ENTER);

    profilePage.save();

    searchPage = new SearchPage();
    searchPage.addProfile();
    searchPage.openLastProfile();

    degrees = profilePage.degrees;
    degrees.click();
    degrees.sendKeys("Test");

    var suggestionsCount = element.all(by.css('.suggestion-item')).count();
    expect(suggestionsCount).toBe(3);
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
    startField.clear();
    startField.sendKeys("01.01.2012");
    profilePage.save();
    expect(startField.getAttribute('value')).toContain('01.01.12');
  });

  it('should be able to delete a project association', function () {
	  profilePage.addProjectAssociation();
	  var startField = element(by.id('start-0'));
	  startField.click();
	  startField.clear();
	  startField.sendKeys("01.01.2012");
	  profilePage.deleteProjectAssociation();
	  expect(profilePage.projectAssociationCount).toEqual(0);
  });

  it('should be able to delete a project association, after it was saved persistent', function () {
	  profilePage.addProjectAssociation();
	  var startField = element(by.id('start-0'));
	  startField.click();
	  startField.clear();
	  startField.sendKeys("01.01.2012");
	  profilePage.save();
	  profilePage.deleteProjectAssociation();
	  profilePage.save();
	  expect(profilePage.projectAssociationCount).toEqual(0);
  });

  it('project associations should not be deleted, when cancel and reject button button are pressed', function () {
	  profilePage.addProjectAssociation();
	  var startField = element(by.id('start-0'));
	  startField.click();
	  startField.clear();
	  startField.sendKeys("01.01.2012");
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

  function getFileName() {
    if (browser.inWindows()) {
      return "C:\\Users\\Public\\Pictures\\Sample Pictures\\flagge.gif";
    } else {
      var defaultImage = "../../../resources/fileupload.png";
      return pathUtil.resolve(__dirname, defaultImage);
    }
  }
});
