'use strict';

var ProfilePage = function () {
};

ProfilePage.prototype = Object.create({}, {
  thumbnailPath: {
    get: function () {
      return element(by.id('image')).getAttribute('src');
    }
  },
  firstName: {
    get: function () {
      return element(by.id('firstName'));
    }
  },
  degrees: {
    get: function () {
      return element(by.css('#degrees input[type="text"]'));
    }
  },
  workExperience: {
    get: function () {
      return element(by.id('workExperience'));
    }
  },
  degreeTagCount: {
    get: function () {
      return element.all(by.css('#degrees ti-tag-item')).count();
    }
  },
  save: {
    value: function () {
      element(by.id('save-button')).click();
    }
  },
  cancel: {
    value: function () {
      element(by.id('cancel-button')).click();
    }
  },
  uploadImage: {
    value: function (path) {
      element(by.model('files')).click();
      browser.executeScript('$(\'input[type="file"]\').css(\'visibility\', \'visible\');');
      element(by.css('input[type="file"]')).sendKeys(path);
    }
  }

});

module.exports = ProfilePage;