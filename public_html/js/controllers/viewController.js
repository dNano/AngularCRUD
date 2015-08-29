/*
 * Controller for the view details page.
 */

"use strict";

angular.module("angularcrud")
.controller("ViewCtrl", function ($scope, $location, $routeParams, dataFactory) {
   dataFactory.getById($routeParams.id, function (data) {
      $scope.contact = data;

      // We are offline. Localforage operations happen outside of Angular's view, tell Angular data changed
      if (!$scope.online) {
         $scope.$apply();
      }
   });

   $scope.remove = function () {
      dataFactory.delete($scope.contact.id, function() {
         $location.path("/");
      });
   };

   $scope.edit = function () {
      $location.path("/edit/" + $scope.contact.id);
   };

   $("#menu-list").removeClass("active");
   $("#menu-new").removeClass("active");
   $("#menu-settings").removeClass("active");
});
