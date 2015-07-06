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
  reject: {
	value: function () {
	  element(by.id('reject-button')).click();
	}
  },
  dashboard: {
	value: function () {
	   element(by.id('dashboard')).click();
	}
  },
  
  uploadImage: {
    value: function (path) {
      element(by.model('files')).click();
      browser.executeScript('$(\'input[type="file"]\').css(\'visibility\', \'visible\');');
      element(by.css('input[type="file"]')).sendKeys(path);
    }
  },
  addProjectAssociation: {
    value: function () {
      element(by.id('add-project-association')).click();
    }
  },
  selectLastProjectInLastProjectAssociation: {
    value: function () {
      var projectAssociation = element.all(by.css('.project-association')).last();
      var project = projectAssociation.element(by.model('projectAssociation.project'));
      project.element(by.css('select option:last-child')).click();
    }
  },
  deleteProjectAssociation: {
    value: function () {
      element(by.id('delete-project-association-button-0')).click();
    }
  }

});

module.exports = ProfilePage;