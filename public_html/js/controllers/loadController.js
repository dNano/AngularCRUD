/*
 * Controller for reinitializing the database.
 */
"use strict";

angular.module("angularcrud")
.controller("LoadCtrl", function ($location, restFactory, SAMPLEDATA) {
   restFactory.updateAll(SAMPLEDATA, function() {
      $location.path("/");
   });
});
