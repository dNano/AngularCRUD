/* global exports, jasmine */

exports.config = {

   framework: "jasmine2",        // Default is Jasmine 1.3, This will use jasmine 2.x
   specs: ["controllers/spec.*.js"],
   multiCapabilities: [
      { browserName: "firefox"},
      { browserName: "chrome" }
   ],

   onPrepare: function() {
      // Terminal reporter
      var SpecReporter = require("./../../gulp/node_modules/jasmine-spec-reporter");
      jasmine.getEnv().addReporter(new SpecReporter({displayStacktrace: true}));
   }

};
