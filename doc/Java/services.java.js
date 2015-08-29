/*
 * Services to interact with our data storage - Server using REST Web Services
 * and local data storage via localForage
 */
angular.module("angularcrud")
   /*
    * Our controllers interact with dataFactory which is a facade for server or local storage. If we have
    * a network connection, we use our rest service. Otherwise we use our local storage service.
    */
   .factory("dataFactory", function ($rootScope, restFactory, forageFactory) {
      return {
         getAll: function (successCallback) {
            if ($rootScope.online) {
               restFactory.getAll(successCallback);
            }
            else {
               forageFactory.getAll(successCallback);
            }
         },
         getById: function (id, successCallback) {
            if ($rootScope.online) {
               restFactory.getById(id, successCallback);
            }
            else {
               forageFactory.getById(id, successCallback);
            }
         },
         delete: function (id) {
            if ($rootScope.online) {
               restFactory.delete(id);
            }
            else {
               forageFactory.delete(id);
            }
         },
         update: function (id, first, last) {
            if ($rootScope.online) {
               restFactory.update(id, first, last);
            }
            else {
               forageFactory.update(id, first, last);
            }
         },
         add: function (first, last) {
            if ($rootScope.online) {
               restFactory.add(first, last);
            }
            else {
               forageFactory.add(first, last);
            }
         },
         updateAll: function () {
            localforage.getItem($rootScope.RESTURL, function (contacts) {
               restFactory.updateAll(contacts);
            });
         }
      };
   })
   // Data interface, called by dataFactory for server storage. This is used when we have a network connection.
   .factory("restFactory", function ($rootScope, $http, $location) {
      return {
         getAll: function (successCallback) {
            $http.get($rootScope.RESTURL).success(successCallback);
            // This was with default JAXB which returns data as person with array for values - unless there's only one value
//               .success( function(data) {
//                  successCallback(data.person);
//               });
         },
         getById: function (id, successCallback) {
            $http.get($rootScope.RESTURL + id).success(successCallback);
         },
         delete: function (id) {
            $http.delete($rootScope.RESTURL + id).success(function () {
               $location.path("/");
            });
         },
         update: function (id, first, last) {
            $http({
               url: $rootScope.RESTURL,
               data: {"id": id, "firstName":first, "lastName":last},
               method: "PUT"
            }).success(function (data, status, headers, config) {
               $location.path("/view/" + id);
            });
         },
         add: function (first, last) {
            $http.post($rootScope.RESTURL, {firstName:first, lastName:last})
                 .success(function () {
                    $location.path("/");
                 });
         },
         updateAll: function (data) {
            $http.delete($rootScope.RESTURL + "deleteall")
               .success(function () {
                  for (var i = 0; i < data.length; i++) {      // Iterate through local data saving to server
                     $http.post($rootScope.RESTURL, {firstName:data[i].firstName, lastName:data[i].lastName});
                  }
                  $location.path("/");
               });
         }
      };
   })
   // Data interface, called by dataFactory for local storage. This is used when we don't have a network connection.
   .factory("forageFactory", function ($rootScope, $location) {
      return {
         getAll: function (successCallback) {
            localforage.getItem($rootScope.RESTURL, successCallback);
         },
         getById: function (id, successCallback) {
            localforage.getItem($rootScope.RESTURL, function (contact) {
               successCallback(contact[id]);
            });
         },
         delete: function (id) {
            localforage.getItem($rootScope.RESTURL, function (contact) {
               delete contact[id];
               localforage.setItem($rootScope.RESTURL, contact, function (data) {
                  $rootScope.$apply(function() {
                     $location.path("/");
                  });
               });
            });
         },
         update: function (id, first, last) {
            localforage.getItem($rootScope.RESTURL, function (contact) {
               contact[id].firstName = first;
               contact[id].lastName = last;
               // Note syntax for attribute that starts with a period
               contact[id][".priority"] = last.toLowerCase() + " " + first.toLowerCase();
               localforage.setItem($rootScope.RESTURL, contact, function (data) {
                  $rootScope.$apply(function() {
                     $location.path("/view/" + id);
                  });
               });
            });
         },
         add: function (first, last) {
            var id = "-";
            var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

            for (var i = 0; i < 19; ++i) {
               id += possible.charAt(Math.floor(Math.random() * possible.length));
            }

            localforage.getItem($rootScope.RESTURL, function (contact) {
               contact[id] = {};
               contact[id].firstName = first;
               contact[id].lastName = last;
               localforage.setItem($rootScope.$rootScope.RESTURL, contact, function (data) {
                  $rootScope.$apply(function() {
                     $location.path("/");
                  });
               });
            });
         }
      };
   });
