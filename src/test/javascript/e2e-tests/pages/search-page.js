var SearchPage = function () {
    this.addProfileButton = element(by.id('own-profile')).click();
    this.profileCount = element(by.id('profile-count'));
    this.firstProfile = element(by.className('profile'));

    this.get = function () {
        browser.get('/search');
    };

    this.addProfile = function () {
        element(by.id('profile-count')).getText().then(function (oldCount) {
            element(by.id('own-profile')).click();
            setTimeout(function () {
                element(by.id('profile-count')).getText().then(function (newCount) {
                    expect(parseInt(newCount)).toBeGreaterThan(parseInt(oldCount));
                });
                done();
            }, 1000);
        });
    };
};