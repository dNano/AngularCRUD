// "describe" sets up a test suite
describe("httpFactory Test", function () {
    // Arrange
    var service, backend;

   // Create mock Timecard Review module before each test
    beforeEach(angular.mock.module("timecardReview"));

    // $httpBackend which gives us the ability to make HTTP requests in our tests without
    // having to rely on a back end, we can test our application in a vacuum.
    beforeEach(angular.mock.inject(function (httpFactory, $httpBackend) {
        service = httpFactory;
        backend = $httpBackend;
    }));

    // Act & Assert
    // "it" is a unit test
    it('should have a httpFactory service', function() {
        expect(service).toBeDefined();             // Make sure our restFactory is defined.
    });

   // Checks that all expected requests have been received. Nothing
   // is stalling our HTTP calls.
    it("have all Ajax requests been received", function () {
        backend.verifyNoOutstandingExpectation();
    });
});