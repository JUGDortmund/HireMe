var myip = require('my-local-ip');

function getUrl() {
  var url = "http://" + myip() + ":8080";
  return url;
}

function httpGet(siteUrl) {
  var http = require('http');
  var defer = protractor.promise.defer();
  mySiteUrl = getUrl() + siteUrl;
  http.get(mySiteUrl, function(response) {
    var bodyString = '';
    response.setEncoding('utf8');
    response.on("data", function(chunk) {
      bodyString += chunk;
    });
    response.on('end', function() {
      defer.fulfill({
        statusCode: response.statusCode,
        bodyString: bodyString
      });
    });
  }).on('error', function(e) {
    defer.reject("Got http.get error: " + e.message);
  });
  return defer.promise;
}

module.exports = {
  httpGet : httpGet
};
