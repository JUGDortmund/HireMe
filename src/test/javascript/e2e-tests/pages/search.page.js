'use strict';

var SearchPage = function () {
  browser.get('/search');
};

SearchPage.prototype = Object.create({}, {
  searchInputField: {
    get: function () {
      return element(by.model('search'));
    }
  },
  addProfileButton: {
    get: function () {
      return element(by.id('own-profile'));
    }
  },
  profileCount: {
    get: function () {
      return element(by.id('profile-count')).getText().then(function (text) {
        return parseInt(text);
      });
    }
  },  
  filteredProfileCount: {
    get: function () {
      return element.all(by.className('profile')).count();
    }
  },
  lastProfile: {
    get: function () {
      return element.all(by.className('profile')).last();
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
  },
  searchProfile: {
    value: function (searchKeyword) {
      this.searchInputField.clear();
      this.searchInputField.sendKeys(searchKeyword);
    }
  }, 
});

module.exports = SearchPage;