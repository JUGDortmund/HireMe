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

  it('should save the new name of the selected user after save button is pressed', function () {
    var expectedText = 'newUserFirstName';
    var firstname = element(by.id('firstName'));
    firstname.click();
    firstname.clear();
    firstname.sendKeys(expectedText);
    element(by.id('save-button')).click();
    expect(firstname.getAttribute('value')).toBe(expectedText);
  });

  it('should reset the new name of the selected user after cancel button is pressed', function () {
    var newText = 'newUserFirstName';
    var firstname = element(by.id('firstName'));
    var oldText = firstname.getAttribute('value');
    firstname.click();
    firstname.clear();
    firstname.sendKeys(newText);
    element(by.id('cancel-button')).click();
    expect(firstname.getAttribute('value')).toBe(oldText);
  });

  it('should be able to create a new tag', function () {
    var degrees = element(by.css('#degrees input[type="text"]'));
    degrees.click();
    degrees.sendKeys("Test");

    var tagCount = element.all(by.tagName('ti-tag-item')).count();

    expect(tagCount).toBe(1);
  });

  it('should be able to remove an existing tag', function () {
    var degrees = element(by.css('#degrees input[type="text"]'));
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

    var tagCount = element.all(by.tagName('ti-tag-item')).count();
    expect(tagCount).toBe(3);
  });

  it('should provide suggestions for tag fields from other profiles', function () {
    var degrees = element(by.css('#degrees input[type="text"]'));
    degrees.click();
    degrees.sendKeys("Test123");
    degrees.sendKeys("Test124");
    degrees.sendKeys(protractor.Key.ENTER);

    element(by.id('save-button')).click();

    searchPage = new SearchPage();
    searchPage.addProfile();
    searchPage.lastProfile.element(by.css('a .info-box-content')).click();
    browser.wait(function () {
      return browser.getCurrentUrl().then(function (url) {
        return url.indexOf('/profile/') > -1;
      });
    }, 500);

    var degrees = element(by.css('#degrees input[type="text"]'));
    degrees.click();
    degrees.sendKeys("Test");

    var tagCount = element.all(by.css('.suggestion-item')).count();
    expect(tagCount).toBe(1);
  });

});
