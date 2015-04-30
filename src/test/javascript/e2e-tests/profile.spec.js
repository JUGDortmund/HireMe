'use strict';

var SearchPage = require('./pages/search.page.js');

describe('profile page', function () {

  var searchPage;
  beforeEach(function () {
    searchPage = new SearchPage();
    searchPage.addProfile();
  });

  it('should be displayed if a profile with a valid id is called', function () {
    searchPage.firstProfile.click();
  });

});
