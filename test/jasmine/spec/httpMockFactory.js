angular.module("timecardReview")
.factory("httpMockFactory", function($httpBackend, URL, toaster) {

   return {
        getEmployee: function getEmployeeDetail (badgeno, weekEndingDate, successCallback) {
           var url = URL + "employee/" + badgeno + "/" + weekEndingDate;

            // For now we'll return an empty object.
            // TODO - maybe for the sake of completeness, test a failure?
            successCallback({});
        },
       
      /**
       * Get the direct reports for a manager
       *
       * @param {type} employeeNumber Managers employee number
       * @param {type} weekEndingDate Week ending data that we're looking at
       * @param {type} successCallback Code to run on a successful web service execution
       * @returns {undefined}
       */
      getDirectReportsByManager: function (employeeNumber, weekEndingDate, successCallback) {
       var url = URL + employeeNumber + "/" + weekEndingDate + "/";   // Production url
//         var url = "testdata/directreports.json";   // Testing url
        // For now we'll return a 1-length array of an empty object.
        // TODO - maybe for the sake of completeness, test a failure?
        successCallback([{}]);
        
        // This is the actual controller code when an error occurs . . .
            /*.error(function (data, status, headers, config) {
               console.log("httpFactory.getDirectReportsByManager() Error: " + data);
               toaster.pop("error", "Holy Non Sequiturs Batman", "The REST Web Service call getDirectReportsByManager " + url + " failed.");
            });*/

      },

      getDirectReportDetail: function (employeeNumber, weekEndingDate, successCallback) {
         var url = URL + "directreportdetail/" + employeeNumber + "/" + weekEndingDate + "/";   // Production url
            // For now we'll return an empty object.
            successCallback({
                productiveInfo: [],
                leavePayType: [],
                employee: {}
            });
        },

      /**
       * Get employee timecard review information. If this is the first time
       * this employee's data is being reviewed, no data will be returned by
       * the web service. This function will initialize a blank JavaScript
       * object if no data was returned.
       *
       * @param {string} employeeNumber Managers employee number
       * @param {string} weekEndingDate Week ending data that we're looking at
       * @param {function} successCallback Code to run on a successful web service execution
       * @param {function} failureCallback Code to run on a failed web service execution
       * @returns {undefined}
       */
      getEmployeeReview: function(employeeNumber, weekEndingDate, successCallback, failureCallback) {
         var url = URL + "directreportdetail/" + employeeNumber + "/" + weekEndingDate + "/";   // Production url
            // For now we'll return a (mostly) empty object.
            // Controller won't load if productiveInfo, leavePayType are null . . .
            // TODO - maybe for the sake of completeness, test a failure?
            // TODO - use this for testing sums!
            successCallback({});
        },

      /**
       * Write a log message
       *
       * @param {type} logText Message content to be written to the application log
       * @param {type} successCallback Code to run on a successful web service execution
       * @returns {undefined}
       */
      writeLog : function (logText, successCallback) {
        var url = URL + "log";   // Production url
        // Always return success, because we have NO Backend!
        // TODO - maybe for the sake of completeness, test a failure?
        successCallback("data");
        
        // This is the actual controller code when an error occurs . . .
            /*.error(function (data, status, headers, config) {
               console.log("httpFactory.writeLog() Error: " + data);
               toaster.error("Holy Non Sequiturs Batman", "The REST Web Service call to " + url + " failed.");
            });*/
      }

   };
});