'use strict';

var SearchPage = require('../pages/search.page.js');

describe('profile page', function () {

  var searchPage;
  beforeEach(function () {
    searchPage = new SearchPage();
    searchPage.addProfile();
    searchPage.lastProfile.element(by.css('a .info-box-content')).click();
    browser.wait(function () {
      return browser.getCurrentUrl().then(function (url) {
        return url.indexOf('/profile/') > -1;
      });
    }, 500);
  });

  it('should show the name of the selected user', function () {
    expect(element(by.id('firstName')).getAttribute('value')).toBe('Max');
  });

  it('should redirect if a profile with a valid id is called', function () {
    expect(browser.getCurrentUrl()).toContain("/profile/");
  });

  it('should save the new name of the selected user after save button is pressed', function() {
    var expectedText = 'newUserFirstName';
    var firstname = element(by.id('firstName'));
    firstname.click();
    firstname.clear();
    firstname.sendKeys(expectedText);
    element(by.id('save-button')).click();
    expect(firstname.getAttribute('value')).toBe(expectedText);
  });

  it('should reset the new name of the selected user after cancel button is pressed', function() {
    var newText = 'newUserFirstName';
    var firstname = element(by.id('firstName'));
    var oldText = firstname.getAttribute('value');
    firstname.click();
    firstname.clear();
    firstname.sendKeys(newText);
    element(by.id('cancel-button')).click();
    expect(firstname.getAttribute('value')).toBe(oldText);
  });

  it('should set a date correctly and persist date to profile', function() {
    var inputDate = '01.03.01';
    var inputWorkExperience = element(by.id('workExperience'));
    inputWorkExperience.click();
    inputWorkExperience.clear();
    inputWorkExperience.sendKeys(inputDate);
    element(by.id('save-button')).click();
    expect(inputWorkExperience.getAttribute('value')).toBe(inputDate);
  });

  it('should reject a date before persisting', function() {
    var incorrectInputDate = '01.0x.01';
    var inputWorkExperience = element(by.id('workExperience'));
    inputWorkExperience.clear();
    inputWorkExperience.sendKeys(incorrectInputDate);
    expect(element(by.id('save-button')).isDisplayed()).toBe(false);
    expect(element(by.id('cancel-button')).isDisplayed()).toBe(false);
  });

  it('should set a date correctly and persist after correcting a falsy date', function() {
    var incorrectInputDate = '01.0x.01';
    var inputWorkExperience = element(by.id('workExperience'));
    inputWorkExperience.click();
    inputWorkExperience.clear();
    inputWorkExperience.sendKeys(incorrectInputDate);
    inputWorkExperience.sendKeys(protractor.Key.DOWN);
    inputWorkExperience.sendKeys(protractor.Key.ENTER);
    inputWorkExperience.click();
    var newValue = inputWorkExperience.getAttribute('value');
    element(by.id('save-button')).click();
    expect(inputWorkExperience.getAttribute('value')).toBe(newValue);
  });





});
