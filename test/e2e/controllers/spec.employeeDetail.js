/* global browser, expect, by, element */

describe("Employee Detail", function() {

   // Setup - runs before each test
   beforeEach(function() {
      // Our URL is prefixed with baseUrl from config.js
      browser.get("timecardreview/#/viewdetail/113626/104769/2015-07-05"); // Thu-Ha for July 7 2015
   });

   // Tear-down - runs after each test
   afterEach(function() { });


   it("We can load the employee detail page directly", function() {

      // Check page title
      expect(browser.getTitle()).toEqual("Timecard Review");

      // Check Emp Detail screen has the requisite objects
      expect(element(by.id("datePicker")).isPresent()).toBe(true);

      expect(element(by.id("empName")).isPresent()).toBe(true);
      expect(element(by.id("empBadge")).isPresent()).toBe(true);
      expect(element(by.id("empSchedule")).isPresent()).toBe(true);

      expect(element(by.id("mgrName")).isPresent()).toBe(true);
      expect(element(by.id("mgrOrg")).isPresent()).toBe(true);

      expect(element(by.id("tabAllocations")).isPresent()).toBe(true);
      expect(element(by.id("allocationsGrid")).isPresent()).toBe(true);
      expect(element(by.id("lptTable")).isPresent()).toBe(true);

      expect(element(by.id("tabAccruals")).isPresent()).toBe(true);

      expect(element(by.id("tabWAM")).isPresent()).toBe(true);
      expect(element(by.id("wamTable")).isPresent()).toBe(true);
   });


   it("Data loaded properly", function() {

      // Check employee is Thu-Ha
      expect(element(by.id("empName")).getText()).toBe("ThuHa N Truong");

      // Check Emp Detail screen has the correct data values for this mgr/emp/wed combination
      expect(element(by.id("empName")).getText()).toBe("ThuHa N Truong");
      expect(element(by.id("empBadge")).getText()).toBe("104769");
      expect(element(by.id("empSchedule")).getText()).toBe("9/80");

      expect(element(by.id("mgrName")).getText()).toBe("Camille T Sunabe");
      expect(element(by.id("mgrOrg")).getText()).toBe("2205");

      // Tables/Grids are the correct size
//      expect(element.all(by.repeater("pi in directReport.productiveInfo")).count()).toBe(3); // Note - This doesn't include the total row added manually
      expect(element.all(by.repeater("lpt in lptCollapsed")).count()).toBe(1);               // 4th of July holiday

      // This grid is a placeholder for now
//    expect(element(by.id("allocationsGrid")).isPresent()).toBe(true);

      /* NOTE - getText() fails on FF because tab is hidden?
       *    Even doing the following didn't help:
       *       element(by.id("tabAccruals")).click();
       *       browser.waitForAngular();
       *
       *    Switching to getInnerHtml() does work on FF
       */
      expect(element(by.id("vacation")).getInnerHtml()).toBe("xxx");
      expect(element(by.id("sick")).getInnerHtml()).toBe("yyy");

      // Will be converted to by.repeater when this is implemented
      expect(element.all(by.repeater("wam in directReport.wam")).count()).toBe(4);
   });

});
