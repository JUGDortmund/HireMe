'use strict';

var SearchPage = function () {
  browser.get('/search');
};

SearchPage.prototype = Object.create({}, {
  addProfileButton: {
    get: function () {
      return element(by.id('own-profile'));
    }
  },
  profileCount: {
    get: function () {
      return element.all(by.className('profile')).count();
    }
  },
  lastProfile: {
    get: function () {
      return element.all(by.className('profile')).first();
    }
  },
  addProfile: {
    value: function () {
      var page = this;
      page.addProfileButton.click().then(function () {
        browser.wait(function () {
          return page.profileCount.then(function (count) {
            return count > 0;
          });
        }, 500);
      });
    }
  }
});

module.exports = SearchPage;