describe('dashboard', function () {
    it('redirect to \"/search?q=blub\" if \"blub\" is entered in search-form and button \"btn-success\" is clicked',
       function () {
           browser.get('/dashboard');
           element(by.model('search')).sendKeys('blub');
           element(by.css('.btn-success')).click();
           expect(browser.getLocationAbsUrl()).toEqual("/search?q=blub");
       });

    it('redirect to \"/search?q=blub\" if \"blub\" is entered in search-form and \"enter\" is triggered',
       function () {
           browser.get('/dashboard');
           var searchElement = element(by.model('search'));
           searchElement.sendKeys('blub');
           searchElement.sendKeys(protractor.Key.ENTER);
           expect(browser.getLocationAbsUrl()).toEqual("/search?q=blubcv");
       });
});
