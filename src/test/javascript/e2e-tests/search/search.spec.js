'use strict';
var mock = require('protractor-http-mock');
var SearchPage = require('../pages/search.page.js');

describe('search page', function() {

  var searchPage;

  beforeEach(function() {
    mock(['searchProfilesMock']);
    searchPage = new SearchPage();
  });

  it('should show all profiles if search keyword is empty', function() {
    searchPage.searchInputField.clear();
    searchPage.searchInputField.sendKeys("");
    expect(element(by.id('search-results-none')).isDisplayed()).toBeFalsy();
    expect(searchPage.filteredProfileCount).toBe(3);
    expect(searchPage.profileCount).toBe(3);
  });
  
  it('should not show any profile if search keyword does not match', function() {
    searchPage.searchInputField.clear();
    searchPage.searchInputField.sendKeys("LONG_STRING_HOPEFULLY_NOT_MATCHING_ANY_PROFILES");
    expect(searchPage.filteredProfileCount).toBe(0);
    expect(searchPage.profileCount).toBe(3);
    expect(element(by.id('search-results-none')).isDisplayed()).toBeTruthy();
  });
  
  it('should show profiles containing \"Manager\" if search keyword is \"Manager\"', function() {
    searchPage.searchInputField.clear();
    searchPage.searchInputField.sendKeys("Manager");
    expect(element(by.id('search-results-none')).isDisplayed()).toBeFalsy();
    expect(searchPage.filteredProfileCount).toBe(2);
    expect(searchPage.profileCount).toBe(3);
    
    searchPage.searchInputField.clear();
    searchPage.searchInputField.sendKeys("Moritz");
    expect(element(by.id('search-results-none')).isDisplayed()).toBeFalsy();
    expect(searchPage.filteredProfileCount).toBe(1);
    expect(searchPage.profileCount).toBe(3);
  });
  
  it('should search for \"blub\" if search page was called with request parameter \"/search?q=blub\"', function() {
    browser.get('/search?q=blub');
    expect(searchPage.searchInputField.getAttribute('value')).toBe('blub');
  });
  
});
