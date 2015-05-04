module.exports = {
    request: {
        path: '/api/profile',
        method: 'GET'
    },
    response: {
        data: [{"id":"1","firstname":"Max","lastname":"Mustermann","careerStage":"Manager"},
               {"id":"2","firstname":"Moritz","lastname":"Mustermann","careerStage":"Consultant"},
               {"id":"3","firstname":"Max","lastname":"Möchtegern","careerStage":"Manager"}]
    }
};
