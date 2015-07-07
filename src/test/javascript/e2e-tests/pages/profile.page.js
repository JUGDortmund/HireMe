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
      return this.projectAssociations.count();
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
  projectAssociations: {
    get : function () {
      return element.all(by.css('.project-association'));
    }
  },
  lastProjectAssociation: {
    get : function () {
      return this.projectAssociations.last();
    }
  },
  selectLastProjectInLastProjectAssociation: {
    value: function () {
      var project = this.lastProjectAssociation.element(by.model('projectAssociation.project'));
      project.element(by.css('select option:last-child')).click();
    }
  },
  deleteProjectAssociation: {
    value: function () {
      element(by.id('delete-project-association-button-0')).click();
    }
  },
  msgSuccess : {
    get: function () {
      return element(by.id('profile-msg-success'));
    }
  },
  msgError : {
    get: function () {
      return element(by.id('profile-msg-error'));
    }
  },
  getLastTechnologieText: {
	get: function () {
	  return element.all(by.css('#projectAssociations-technologies-0 .tag-item span')).last().getText();
	}
  },
  getLastLocationText: {
		get: function () {
		  return element.all(by.css('#projectAssociations-locations-0 .tag-item span')).last().getText();
		}
	  }
});

module.exports = ProfilePage;