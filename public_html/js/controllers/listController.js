/* global localforage */

/*
 * Controller for the listing page.
 */

"use strict";

angular.module("angularcrud")
.controller("ListCtrl", function ($rootScope, $location, $scope, dataFactory) {
   if ($rootScope.RESTURL === null) {
      $location.path("/settings");
   } else {
      dataFactory.getAll(function (data) {
         $scope.contacts = data;

         // Save the retrieved data locally in case we go offline
         if ($scope.online) {
            localforage.setItem($rootScope.RESTURL, data, function(err, value) { });
         }
         else {
            // We are offline. localForage operations happen outside of Angular's view, tell Angular data changed
            $scope.$apply();
         }
      });

      $("#menu-list").addClass("active");
      $("#menu-new").removeClass("active");
      $("#menu-settings").removeClass("active");
   }
});
