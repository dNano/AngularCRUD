/*
 * This file contains application level functions. App routing, sample data
 * and setup (constants, listeners...).
 */

"use strict";

/* We need a dot in the $scope variable. We cannot just create $scope.id and use it
 * with a ng-xx directive, instead we create "id" as a contact property ($scope.contact.id).
 *
 * http://zcourts.com/2013/05/31/angularjs-if-you-dont-have-a-dot-youre-doing-it-wrong/
 */

// Define our application module.
angular.module("angularcrud", ["ngRoute", "ngMessages"])

// Configure our applications routing.
.config(["$routeProvider", function ($routeProvider) {
   $routeProvider
      .when("/", {
         controller: "ListCtrl",
         templateUrl: "./views/list.html"
      })

      .when("/edit/:id", {
         controller: "EditCtrl",
         templateUrl: "./views/edit.html"
      })

      .when("/view/:id", {
         controller: "ViewCtrl",
         templateUrl: "./views/view.html"
      })

      .when("/new", {
         controller: "NewCtrl",
         templateUrl: "./views/edit.html"
      })

      .when("/load", {
         controller: "LoadCtrl",
         templateUrl: "./views/load.html"
      })

      .when("/settings", {
         controller: "SettingsCtrl",
         templateUrl: "./views/settings.html"
      })

      .otherwise({
         redirectTo: "/"
      });
}])

// Add your REST Web Service application URL here.
// app.constant("RESTURL", "/angularcrud/ws/person/");

// Initial angularcrud data.
.constant("SAMPLEDATA",
   [
      {"firstName": "Fred",  "lastName": "Flintstone"},
      {"firstName": "Wilma", "lastName": "Flintstone"},
      {"firstName": "Barney","lastName": "Rubble"},
      {"firstName": "Betty", "lastName": "Rubble"}
   ]
)


// Called on application start up.
.run(function($window, $rootScope, dataFactory) {
   // Function to run when we transition to being online
   function onOnline() {
      $rootScope.$apply(function() {
         // If we were previously offline, push all local changes to the server
         if (!$rootScope.online) {
            dataFactory.updateAll();
         }
         $rootScope.online = true;
      });
   }

   // Function to run when we transition to being offline
   function onOffline() {
      $rootScope.$apply(function() {
         $rootScope.online = false;
      });
   }

   // Variable containing network status (online true or false), note we don't (but should) test access to our
   // REST Web Service. We're assuming since we have a network connection we must be able to access our data!
   $rootScope.online = $window.navigator.onLine;

   // Set our on/off line functions as event listeners
   $window.addEventListener("offline", onOffline, false);
   $window.addEventListener("online",  onOnline,  false);

   // Get the REST web service URL from localStorage. If this is the first run (or localStorage
   // has been cleared) the returned value will be null. If null the user will be redirected to
   // the settings page where it can be set.
   //
   // In localStorage, if the key isn't found null is returned
   $rootScope.RESTURL = localStorage.getItem("RESTURL");
//   if ($rootScope.RESTURL === null) {
//      $rootScope.RESTURL = "/angularcrud/ws/person/";  // Java
//      $rootScope.RESTURL = "/angularcrudperson/";      // Elasticsearch
//      localStorage.setItem("RESTURL", $rootScope.RESTURL);   // Persist the URL to localStorage for future use
//   }

});
