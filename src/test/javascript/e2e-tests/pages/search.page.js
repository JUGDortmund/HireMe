'use strict';

var SearchPage = function() {
  browser.get('/search');
};

SearchPage.prototype = Object.create({}, {
  addProfileButton: {
    get: function() {
      return element(by.id('own-profile'));
    }
  },
  profileCount: {
    get: function() {
      return element.all(by.className('profile')).count();
    }
  },
  firstProfile: {
    get: function() {
      return element(by.className('profile'))
    }
  },
  addProfile: {
    value: function() {
//      this.profileCount.getText().then(function(oldCount) {
        this.addProfileButton.click();
//        setTimeout(function() {
//          this.profileCount.getText().then(function(newCount) {
//            expect(parseInt(newCount)).toBeGreaterThan(parseInt(oldCount));
//          });
//          done();
//        }, 1000);
//      });
    }
  }
});

module.exports = SearchPage;