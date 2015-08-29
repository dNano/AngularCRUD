/*
 * Controller for the settings page.
 */

"use strict";

angular.module("angularcrud")
.controller("SettingsCtrl", function ($rootScope, $scope, $location) {
   $scope.settings = {};

   // Set default value to be used for form input field
   if ($rootScope.RESTURL === null) {
      $scope.settings.url = "https://YADDA-YADDA/";
   } else {
      $scope.settings.url = $rootScope.RESTURL;
   }

   // Save new settings
   $scope.save = function () {
      // Really should test that the url is accessible and is a valid data url

      // Make sure URL ends with "/"
      if ($scope.settings.url.slice(-1) !== "/") {
         $scope.settings.url += "/";
      }

      localStorage.setItem("RESTURL", $scope.settings.url);   // Persist the URL to localStorage for future use
      $rootScope.RESTURL = $scope.settings.url;               // Set the app runtime URL variable

      // Re-enable other tabs now that we have a URL
      $("#menu-list").removeClass("disabled");
      $("#menu-new").removeClass("disabled");
      $("#menu-loaddata").removeClass("disabled");

      $location.path("/"); // Go to list screen which will load data from the server
   };

   // Save new settings
   $scope.cancel = function () {
      // Re-enable other tabs now that we have a URL
      $("#menu-list").removeClass("disabled");
      $("#menu-new").removeClass("disabled");
      $("#menu-loaddata").removeClass("disabled");

      $location.path("/"); // Go to list screen which will load data from the server
   };

   // Disable other menu items until a valid data url is entered and make settings tab active with all others inactive
   $("#menu-list")    .addClass("disabled").removeClass("active");
   $("#menu-new")     .addClass("disabled").removeClass("active");
   $("#menu-loaddata").addClass("disabled").removeClass("active");
   $("#menu-settings")                     .addClass("active");
});