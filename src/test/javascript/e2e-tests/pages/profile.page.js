'use strict';

var ProfilePage = function () {
};

ProfilePage.prototype = Object.create({}, {
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
  projectAssociationCount: {
    get: function () {
      return element.all(by.css('.project-association')).count();
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
  addProjectAssociation: {
    value: function () {
      element(by.id('add-project-association')).click();
    }
  }
});

module.exports = ProfilePage;