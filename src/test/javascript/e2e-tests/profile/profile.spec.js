'use strict';

var SearchPage = require('../pages/search.page.js');

describe('profile page', function () {

  var searchPage;
  beforeEach(function () {
    searchPage = new SearchPage();
    searchPage.addProfile();
    searchPage.lastProfile.element(by.tagName('a')).click();
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

});
