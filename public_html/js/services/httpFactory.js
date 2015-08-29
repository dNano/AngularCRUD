/*
 * Services to interact with our data storage - Server using REST Web Services
 * and local data storage via localForage
 */
/* global localforage */

angular.module("angularcrud")
   /*
    * Our controllers interact with dataFactory which is a facade for server or local storage. If we have
    * a network connection, we use our rest service. Otherwise we use our local storage service.
    */
   .factory("httpFactory", function ($rootScope, restFactory, forageFactory) {
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
         delete: function (id, successCallback) {
            if ($rootScope.online) {
               restFactory.delete(id, successCallback);
            }
            else {
               forageFactory.delete(id, successCallback);
            }
         },
         update: function (data, successCallback) {
            if ($rootScope.online) {
               restFactory.update(data, successCallback);
            }
            else {
               forageFactory.update(data, successCallback);
            }
         },
         add: function (data, successCallback) {
            if ($rootScope.online) {
               restFactory.add(data, successCallback);
            }
            else {
               forageFactory.add(data, successCallback);
            }
         },
         updateAll: function () {
            localforage.getItem($rootScope.RESTURL, function (contacts) {
               restFactory.updateAll(contacts);
            });
         }
      };
   })
   /* Data interface, called by httpFactory for server storage. This is used when we have a network connection.
    *
    * Note use of Elasticsearch "?refresh=true" on RESTful service calls.
    *
    * http://www.elastic.co/guide/en/elasticsearch/reference/1.x/docs-index_.html#index-refresh
    * "To refresh the shard (not the whole index) immediately after the operation occurs, so that the document appears
    *  in search results immediately, the refresh parameter can be set to true. Setting this option to true should ONLY
    *  be done after careful thought and verification that it does not lead to poor performance, both from an indexing
    *  and a search standpoint."
    */
   .factory("restFactory", function ($rootScope, $http, $location) {
      return {
         getAll: function (successCallback) {
            $http.get($rootScope.RESTURL + "_search")  // , "{\"sort\" : [{\"lastName\" : {\"order\" : \"asc\"}}]}")
               .success(function(data) {
                  var resultArray = [], oneResult = {};
                  for (i=0; i<data.hits.total; i++) {
                     oneResult = data.hits.hits[i]._source;
                     oneResult.id = data.hits.hits[i]._id;
                     resultArray.push(oneResult);
                  }
                  successCallback(resultArray);
               });
         },
         getById: function (id, successCallback) {
            $http.get($rootScope.RESTURL + id)
               .success(function(data) {
                  var temp;
                  temp = data._source;
                  temp.id = data._id;
                  successCallback(temp);
               });
         },
         delete: function (id, successCallback) {
            $http.delete($rootScope.RESTURL + id + "?refresh=true").success(successCallback);
         },
         update: function (data, successCallback) {
            var id = data.id; // Reuse id (lname + fname) not name as name could have changed!
            delete data.id; // We don't want an embedded id, the _id is one level above _source in ES!
            $http.put($rootScope.RESTURL + id + "?refresh=true", data)   // we use the id not the name as the name could have changed.
               .success(function (data, status, headers, config) {
                  successCallback(id);
               });
         },
         add: function (data, successCallback) {
            // TODO check that id doesn't already exist!
            var id = (data.lastName + data.firstName).toLowerCase();
            $http.put($rootScope.RESTURL + id + "?refresh=true", data)
               .success(
                  successCallback(id)
               );
         },
         updateAll: function (data, successCallback) {
            $http.delete($rootScope.RESTURL)
               // Old data existed
               .success(function () {
                  for (var i = 0; i < data.length; i++) {      // Iterate through local data saving to server
                     $http.put(
                        $rootScope.RESTURL + (data[i].lastName + data[i].firstName).toLowerCase() + "?refresh=true", data[i]
                     );
                  }
                  successCallback;
               })
               // Old data didn't exist (or some other error!)
               .error(function () {
                  for (var i = 0; i < data.length; i++) {      // Iterate through local data saving to server
                     $http.put(
                        $rootScope.RESTURL + (data[i].lastName + data[i].firstName).toLowerCase() + "?refresh=true", data[i]
                     );
                  }
                  successCallback;
               });
         }
      };
   })
   /*
    * Data interface, called by dataFactory for local storage. This is used when we don't have a network connection.
    */
   .factory("forageFactory", function ($rootScope) {
      return {
         getAll: function (successCallback) {
            localforage.getItem($rootScope.RESTURL, successCallback);
         },
         getById: function (id, successCallback) {
            localforage.getItem($rootScope.RESTURL, function (contact) {
               successCallback(contact[id]);
            });
         },
         delete: function (id, successCallback) {
            localforage.getItem($rootScope.RESTURL, function (contact) {
               delete contact[id];
               localforage.setItem($rootScope.RESTURL, contact, function (err, data) {
                  $rootScope.$apply(function() {
                     successCallback;
                  });
               });
            });
         },
         update: function (data, successCallback) {
            localforage.getItem($rootScope.RESTURL, function (contact) {
               contact[data.id].firstName = data.firstName;
               contact[data.id].lastName = data.lastName;
               localforage.setItem($rootScope.RESTURL, contact, function (err, data) {
                  $rootScope.$apply(function() {
                     successCallback(data.id);
                  });
               });
            });
         },
         add: function (data, successCallback) {
            var id = "-";
            var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

            for (var i = 0; i < 19; ++i) {
               id += possible.charAt(Math.floor(Math.random() * possible.length));
            }

            localforage.getItem($rootScope.RESTURL, function (contact) {
               contact[id] = {};
               contact[id].firstName = data.firstName;
               contact[id].lastName = data.lastName;
               localforage.setItem($rootScope.$rootScope.RESTURL, contact, function (err, data) {
                  $rootScope.$apply(function() {
                     successCallback;
                  });
               });
            });
         }
      };
   });
