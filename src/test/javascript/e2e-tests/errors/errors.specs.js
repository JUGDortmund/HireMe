var testHelper = require('../testhelper');

var STATUSURL_400 = "/400";
var STATUSURL_401 = "/401";
var STATUSURL_403 = "/403";
var STATUSURL_404 = "/404";
var STATUSURL_500 = "/500";

describe('errorpages', function () {
  beforeEach(function (){
      browser.get("/dashboard");
  });

  it('should get the statusCode \"400\" if a GET-request was set to \"'+STATUSURL_400+'\"', function() {
    testHelper.httpGet(STATUSURL_400).then(function(result) {
      expect(result.statusCode).toBe(400);
    });
  });

  it('should get the statusCode \"401\" if a GET-request was set to \"'+STATUSURL_401+'\"', function() {
    testHelper.httpGet(STATUSURL_401).then(function(result) {
      expect(result.statusCode).toBe(401);
    });
  });

  it('should get the statusCode \"403\" if a GET-request was set to \"'+STATUSURL_403+'\"', function() {
    testHelper.httpGet(STATUSURL_403).then(function(result) {
      expect(result.statusCode).toBe(403);
    });
  });

  it('should get the statusCode \"404\" if a GET-request was set to \"'+STATUSURL_404+'\"', function() {
    testHelper.httpGet(STATUSURL_404).then(function(result) {
      expect(result.statusCode).toBe(404);
    });
  });

  it('should get the statusCode \"500\" if a GET-request was set to \"'+STATUSURL_500+'\"', function() {
    testHelper.httpGet(STATUSURL_500).then(function(result) {
      expect(result.statusCode).toBe(500);
    });
  });

  it('should place a search-input into the content-area of the site ('+STATUSURL_400+')', function() {
    browser.get(STATUSURL_400);
    var searchElement = element(by.model('search'));
    expect(searchElement.isDisplayed()).toBe(true);
  });

  it('should place a search-input into the content-area of the site ('+STATUSURL_401+')', function() {
    browser.get(STATUSURL_401);
    var searchElement = element(by.model('search'));
    expect(searchElement.isDisplayed()).toBe(true);
  });

  it('should place a search-input into the content-area of the site ('+STATUSURL_403+')', function() {
    browser.get(STATUSURL_403);
    var searchElement = element(by.model('search'));
    expect(searchElement.isDisplayed()).toBe(true);
  });

  it('should place a search-input into the content-area of the site ('+STATUSURL_404+')', function() {
    browser.get(STATUSURL_404);
    var searchElement = element(by.model('search'));
    expect(searchElement.isDisplayed()).toBe(true);
  });

  it('should place a search-input into the content-area of the site ('+STATUSURL_500+')', function() {
    browser.get(STATUSURL_500);
    var searchElement = element(by.model('search'));
    expect(searchElement.isDisplayed()).toBe(true);
  });

});


