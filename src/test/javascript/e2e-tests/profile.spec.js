'use strict';
 
var SearchPage = require('./pages/search.page.js');

describe('profile page', function () {

    beforeEach(function () {

        });
//        browser.get('/search');
//      var test = element.all(by.className('profile')).count().then( function(value) {
//        console.log(value);
//      });
      
    });

    it('should be displayed if a profile with a valid id is called', function () {
      var searchPage = new SearchPage();
      searchPage.addProfile();
      browser.sleep(500);
      searchPage.profileCount.then(function(value) {
        console.log(value);
    });
});

