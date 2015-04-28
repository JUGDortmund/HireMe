describe('navigation-bar', function () {
    it('should create a new profile on click of the profile button', function () {
        browser.get('/search');
        element(by.id('profile-count')).getText().then(function (oldCount) {
            element(by.id('own-profile')).click();
            element(by.id('own-profile')).click();
            expect(element(by.id('profile-count')).getText()).toContain(parseInt(oldCount) + 1);
        });

    });
});