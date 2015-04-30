//describe('search page', function () {
//    'use strict';
//    
//    var initialProfiles;
//
//    beforeEach(function () {
//      browser.get('/search');
//      element(by.id('own-profile')).click();
//      browser.sleep(300);
//      initialProfiles = getCurrentProfileCount();
//    });
//    
//    it('should show all profiles if search keyword is empty',
//       function () {
//          browser.get('/search');
//          expect(initialProfiles).toBe(getCurrentProfileCount())
//       });
//
//    var getCurrentProfileCount = function() {
//      return element.all(by.className('profile')).length;
//    }
//});
