/*
 * Controller for the edit page.
 */
"use strict";

angular.module("angularcrud")
.controller("EditCtrl", function ($scope, $routeParams, $location, dataFactory) {
   dataFactory.getById($routeParams.id, function (data) {
      $scope.contact = data;
//      $scope.contact.id = $routeParams.id; This is put in the data var by the services.js

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

   $scope.save = function () {
      dataFactory.update($scope.contact, function(id) {
         $location.path("/view/" + id);
      });
   };

   $("#menu-list").removeClass("active");
   $("#menu-new").removeClass("active");
   $("#menu-settings").removeClass("active");
});
