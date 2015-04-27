describe('navigation-bar', function () {
    it('should create a new profile on click of the profile button', function () {
        browser.get('/dashboard');
        element(by.id('own-profile')).click();
        browser.get('/search');
        expect(element(by.model('profiles')).count).toEqual(1);
    });
});