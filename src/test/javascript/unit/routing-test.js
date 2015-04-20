describe('angularJS routing suite', function () {

    beforeEach(module('app'));

    it('dashboard route', function () {
        inject(function ($route) {
            expect($route.routes['/dashboard'].templateUrl).toBe('dashboard/dashboard.tpl.html');
        });
    });

    it('profile route', function () {
        inject(function ($route) {
            expect($route.routes['/profile'].templateUrl).toBe('profile/profile.tpl.html');
        });
    });

    it('search route', function () {
        inject(function ($route) {
            expect($route.routes['/search'].templateUrl).toBe('search/search.tpl.html');
        });
    });

    it('default fallback', function () {
        inject(function ($route) {
            expect($route.routes[null].redirectTo).toBe('/dashboard');
        });
    });

});

