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



});
