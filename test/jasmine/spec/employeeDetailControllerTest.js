describe("EmployeeDetailController Test", function () {
    var mockScope = {};
    var controller, kronosStart;
    var ONE_DAY = 24*60*60*1000;

    beforeEach(angular.mock.module("timecardReview")); // Create mock Timecard Review module.

    beforeEach(angular.mock.inject(function ($controller, $rootScope, httpMockFactory) { // Resolve dependencies.
        mockScope = $rootScope.$new(); // Create a new scope.
        controller = $controller("EmployeeDetailController", { // Create an instance of the specified controller.
            $scope: mockScope, // Hook the scope to our new mock scope.
            httpFactory: httpMockFactory,
            KRONOS_IMPLEMENTATION: "2007-01-22"
        });
        kronosStart = new Date();
        kronosStart.setYear(2007);
        kronosStart.setDate(23);
        kronosStart.setMonth(2);
    }));

    it('should have correct implications of review', function() {
        mockScope.review.reviewed = "Y";
        mockScope.setReviewed();
        expect(mockScope.review.followUpRequired).toBe("");
    });

    it('should have correct implications of follow up required', function() {
        mockScope.review.followUpRequired = "Y";
        mockScope.setFollowUp();
        expect(mockScope.review.reviewed).toBe("");
        mockScope.directReport.followUpRequired = "";
        mockScope.setFollowUp();
    });
});