describe("DirectReportsController Test", function () {
    var mockScope = {};
    var controller, kronosStart;
    var ONE_DAY = 24*60*60*1000;

    beforeEach(angular.mock.module("timecardReview")); // Create mock Timecard Review module.

    beforeEach(angular.mock.inject(function ($controller, $rootScope, httpMockFactory) { // Resolve dependencies.
        mockScope = $rootScope.$new(); // Create a new scope.
        controller = $controller("DirectReportsController", { // Create an instance of the specified controller.
            $scope: mockScope, // Hook the scope to our new mock scope.
            httpFactory: httpMockFactory,
            KRONOS_IMPLEMENTATION: "2007-01-22"
        });
        kronosStart = new Date();
        kronosStart.setYear(2007);
        kronosStart.setDate(23);
        kronosStart.setMonth(2);
    }));

    it('should set date correctly', function() {
        var today = new Date();
        var thisYear = today.getFullYear();
        thisYear = thisYear - 2007;
        console.log(thisYear);
        // Check 10 random dates
        for (var dd=0; dd<10; dd++) {
            // year under, overages will be taken care of below . . .
            var year = 2006 + Math.floor((thisYear + 1)*Math.random());
            console.log(year);
            var month = Math.floor(12*Math.random());
            // Take care of case when random actually reaches one, because
            // JS dates are 0-based
            if (month>11) {
                month = 11;
            }
            // Here we are going to hope overflow goes to the next month. . .
            var day = Math.floor(32*Math.random());
            var theTestDate = new Date();
            theTestDate.setFullYear(year);
            theTestDate.setMonth(month);
            theTestDate.setDate(day);
            if (theTestDate.getTime() > today.getTime()) {
                theTestDate = today;
            }
            if (theTestDate.getTime() < kronosStart.getTime()) {
                theTestDate = kronosStart;
            }
            var dayOfWeek = theTestDate.getDay();
            if (dayOfWeek != 0) {
                theTestDate.setTime(theTestDate.getTime() + (7 - dayOfWeek)*ONE_DAY);
            }
            theTestDate.setHours(0);
            theTestDate.setMinutes(0);
            theTestDate.setSeconds(0);
            theTestDate.setMilliseconds(0);
            year = theTestDate.getFullYear();
            month = theTestDate.getMonth() + 1;
            day = theTestDate.getDate();
            var twoDigitDay = day + "";
            if (day<10) {
                twoDigitDay = "" + "0" + twoDigitDay;
            }
            var twoDigitMonth = month + "";
            if (month<10) {
                twoDigitMonth = "" + "0" + twoDigitMonth;
            }
            var input = month + "/" + day + "/" + year;
            console.log(input);
            var output = year + "-" + twoDigitMonth + "-" + twoDigitDay;
            mockScope.setDateFromInput(input);
            expect(mockScope.tcr.userWeekEnding.getTime()).toBe(theTestDate.getTime());
        }
    });

    it('should find direct reports', function() {
        var targetBadgeNo = "123456";
        mockScope.mgrBadgeNo = targetBadgeNo;
        var targetDate = new Date();
        targetDate.setYear(2012);
        targetDate.setDate(11);
        targetDate.setMonth(9);
        mockScope.tcr.userWeekEnding = targetDate;
        var targetDateStr = "2012-10-14";
        // TODO - fill variables to test "$httpBackend"
        // TODO (maybe) - test HTTP failure?
        mockScope.search();
        // For now, the test data is a 1-length list with an empty object. . .
        // See the httpMockFactory file.
        var testBadgeNo = localStorage.getItem("mgrBadgeNo");
        var testDate = localStorage.getItem("weekEndingDate");
        expect(mockScope.org).not.toBe(null);
        expect(mockScope.org.length).toBe(1);
        expect(testBadgeNo).toBe(targetBadgeNo);
        expect(testDate).toBe(targetDateStr);
    });
});