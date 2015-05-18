describe("tagService", function () {
  var tagService, httpBackend;

  beforeEach(module("tags"));

  beforeEach(inject(function (_tagService_, $httpBackend) {
    tagService = _tagService_;
    httpBackend = $httpBackend;

    httpBackend.whenGET("/tags").respond([
      {
        name: 'degree',
        values: [
          'Bachelor',
          'Master'
        ]
      }, {
        name: 'careerLevel',
        values: [
          'Associate',
          'Consultant',
          'Senior Consultant'
        ]
      }
    ]);

    httpBackend.flush();
  }));

  it("should load all tags on start", function () {
    expect(tagService.getTags().length).toBe(2);
  });

  it("should return single tagList by name", function () {
    var result = tagService.getTag('degree');
    expect(result.length).toBe(2);
  });

  it("should return a empty list if no list with the given name is found", function () {
    var result = tagService.getTag('thisIsAFakeTag');
    expect(result).not.toBeUndefined();
    expect(result).not.toBeNull();
    expect(result.length).toBe(0);
  })
});