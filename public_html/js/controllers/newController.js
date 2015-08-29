/*
 * Controller for the new contact page.
 */
"use strict";

angular.module("angularcrud")
.controller("NewCtrl", function ($scope, $location, dataFactory) {
   $scope.contact = {};

   $scope.save = function () {
      dataFactory.add($scope.contact, function(id) {
         $location.path("/view/" + id);
      });
   };

   $("#menu-list").removeClass("active");
   $("#menu-new").addClass("active");
   $("#menu-settings").removeClass("active");
});
