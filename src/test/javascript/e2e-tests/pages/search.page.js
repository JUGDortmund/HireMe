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
  firstProfile: {
    get: function () {
      return element(by.className('profile'))
    }
  },
  addProfile: {
    value: function () {
      var page = this;
      this.profileCount.then(function (oldValue) {
        page.addProfileButton.click();
        page.profileCount.then(function (newValue) {
          browser.wait(function () {
            return oldValue != newValue;
          }, 500);
        });
      });
    }
  }
});

module.exports = SearchPage;