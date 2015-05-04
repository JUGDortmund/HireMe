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
      return element(by.id('profile-count')).getInnerHtml().then(function (html) {
        return parseInt(html);
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
  },
  searchInputField: {
    get: function () {
      return element(by.model('search'));
    }
  },
});

module.exports = SearchPage;