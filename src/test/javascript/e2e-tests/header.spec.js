describe('navigation-bar', function () {
    it('should create a new profile on click of the profile button', function (done) {
        browser.get('/search');
        element(by.id('profile-count')).getText().then(function (oldCount) {
            element(by.id('own-profile')).click();
            setTimeout(function () {
                element(by.id('profile-count')).getText().then(function (newCount) {
                    expect(parseInt(newCount)).toBeGreaterThan(parseInt(oldCount));
                });
                done();
            }, 1000);
        });
    });
});