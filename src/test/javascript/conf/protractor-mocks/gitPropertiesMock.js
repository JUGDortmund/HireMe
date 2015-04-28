module.exports = {
  request: {
    path: '/api/getGitProperties',
    method: 'GET'
  },
  response: {
    data: {
      "gitPropertyDTO": {
        "showGitProperties": true,
        "abbrev": "bc9e5be",
        "commitUserName": "Test Testuser",
        "branch": "feature/TEST-123",
        "commitTime": "27.04.2015 @ 15:08:54 MESZ",
        "buildTime": "27.04.2015 @ 15:15:10 MESZ"
      }
    }
  }
}
