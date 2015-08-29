/* global browser, expect, by, element */

describe("Direct Reports", function() {
   var mgrBadgeNo = element(by.id("mgrBadgeNo"));
   var datePicker = element(by.id("datePicker"));
   var searchButton = element(by.id("searchButton"));

   // Setup - runs before each spec it(){}
   beforeEach(function() {
      /* Start each test will a clean version of the app. We clear the mgrBadgeNo
       * and datePicker as the app loads the previously used values from localStorage.
       */
      browser.get("timecardreview/");             // Is prefixed with baseUrl from config.js
      mgrBadgeNo.clear();
      datePicker.clear();
   });

   // Tear-down - runs after each spec it(){}
   afterEach(function() {

   });


   it("Startup without complaining", function() {
      // Check page title
      expect(browser.getTitle()).toEqual("Timecard Review");

      // Check input fields and data table exist
      expect(element(by.id("mgrBadgeNo")).isPresent()).toBe(true);
      expect(element(by.id("datePicker")).isPresent()).toBe(true);
      expect(element(by.id("drTable")).isPresent()).toBe(true);
   });


   it("Find no direct reports for Jim/119600", function() {
      // Search on Jim for July 5 2015
      mgrBadgeNo.clear().sendKeys("119600");
      datePicker.clear().sendKeys("7/5/2015");
      searchButton.click();

      // Did we get Jim and 7/5/15?
      expect(element(by.id("mgrSpan")).getText()).toBe("Jim Shea");
      expect(element(by.id("wedSpan")).getText()).toBe("7/5/15");

      // Count the number of rows in the dr table
      var rows = element.all(by.repeater("dr in org.directReports"));

      // The table shouldn't have any data rows
      expect(rows.count()).toBe(0);

      // The no direct reports found message should be displayed
      expect(element(by.id("noDrFound")).isDisplayed()).toBeTruthy();
   });


   it("Change to a new period", function() {
      // Search on Jim for July 5 2015
      mgrBadgeNo.clear().sendKeys("119600");

      // Did we get Jim and 7/5/15?
      //expect(element(by.id("mgrSpan")).getText()).toBe("Jim Shea");
      //expect(element(by.id("wedSpan")).getText()).toBe("7/5/15");

      // Change date to 7/8/15, the date should be advanced to the subsequent Sunday
      datePicker.clear().sendKeys("7/8/2015");
      searchButton.click();

      // Did we get Jim and 7/12/15?
      expect(element(by.id("mgrSpan")).getText()).toBe("Jim Shea");
      expect(element(by.id("wedSpan")).getText()).toBe("7/12/15");
   });


   it("Should find 8 employees for Camille/113626", function() {
      // Search on Camille for July 5 2015
      mgrBadgeNo.clear().sendKeys("113626");
      datePicker.clear().sendKeys("7/5/2015");
      searchButton.click();

      // Did we get Camille and 7/5/15?
      expect(element(by.id("mgrSpan")).getText()).toBe("Camille T Sunabe");
      expect(element(by.id("wedSpan")).getText()).toBe("7/5/15");

      // Rows will contain the dynamically created table rows
      var rows = element.all(by.repeater("dr in org.directReports"));
      expect(rows.count()).toBe(8);    // Should be 8 direct reports for this week

      // Check last row is Thu-Ha (note, she changed her name for this week as part of her test data)
      expect(rows.last().element(by.id("empLink8")).getText()).toBe("ThuHa N Truong"); // Using last()
      expect(rows.get(7).element(by.id("empLink8")).getText()).toBe("ThuHa N Truong"); // Using get(#) - probably not so robust

      // The no direct reports found message should not be displayed
      expect(element(by.id("noDrFound")).isDisplayed()).toBeFalsy();
   });


   it("Navigate to employee detail screen", function() {
      // Search on Camille for July 5 2015
      mgrBadgeNo.clear().sendKeys("113626");
      datePicker.clear().sendKeys("7/5/2015");
      searchButton.click();

      // Rows will contain the dynamically created table rows
      var rows = element.all(by.repeater("dr in org.directReports"));

      // Load productive info screen for our last employee
      rows.last().element(by.id("empLink8")).click();

      // Check PI screen has the (some of the) requisite objects
      expect(element(by.id("allocationsGrid")).isPresent()).toBe(true);
      expect(element(by.id("lptTable")).isPresent()).toBe(true);
   });
});