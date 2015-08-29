
# AngularCRUD

This is an example application that demonstrates CRUD (Create, Report, Update and Delete) operations on a simple data model of a lastname and firstname. It's actually two projects:

1. A Java RESTful Web Service setup to run in WebLogic Server 10.3.6 accessing data in an Oracle database. RESTful Web Services are implemented using JAX-RS and the Jersey Reference Implementation of JSR 311.
2. A SPA (Single Page Application) built with the AngularJS and Bootstrap frameworks. AngularJS is the Single Page
Application framework and Bootstrap provides UI formatting and components. The SPA supports offline use via appcache and localStorage. If you're working with just the SPA it's located in the `web` folder (you can ignore the web\WEB-INF folder, it's for the Java WS application).

This is a [NetBeans](https://netbeans.org/) project. While any IDE or text editor can be used to work with the source of this application, it's setup with the Netbeans default folder structure.

## Technologies and Tools
### AngularJS
[AngularJS](http://angularjs.org) is a web application framework for building what's commonly called SPA's (Single Page Applications). It is an Open Source project managed by the [Mountain View Chocolate Factory](http://google.com) .
### Bootstrap
[Bootstrap](http://getbootstrap.com) is a web development framework for making applications responsive and enhancing UI functionality.
### JAX-RS
[JAX-RS](https://jax-rs-spec.java.net/) is the specification for building RESTful Services in Java.
### Jersey
[Jersey](https://jersey.java.net/) is the Reference Implementation of JAX-RS. 
### localForage
Mozilla [localForage](https://github.com/mozilla/localForage) is used for storing data locally in the users browser. localForage will use the most robust storage capability supported by the browser (IndexedDB or localStorage).
### NetBeans
[NetBeans](https://netbeans.org/) is an Open Source IDE that has good Java and SPA support. The latest versions has built in support for AngularJS, Cordova and many other Web application development technologies, frameworks and libraries.

# Folders and files in the application
The following is a quick tour of the folders and files that comprise the application. Not every folder and file
is listed here, just the highlights.

File|Description
----|------------
.gitignore | Files and folders to exclude from git.
bower.json | Bower is a tool for fetching and installing library files used by our SPA.
build.xml | The ant build file for Netbeans. This will create a Java War file to be loaded into WebLogic server. The War file contains both the server side web services and the client application.
read.md | This is the file you're reading!

## \doc
Scripts for creating the RDBMS objects, loading data and other misc documentation.

## \lib
Libraries used by the Java application. Primarily JAX-RS and Jersey jar files.

## \nbproject
Netbeans settings files, not normally edited by a developer.

## \src
Source code for the server side Java application (REST services).

## \web
The client portion of the application. This is the AngularJS/Bootstrap SPA.

File|Description
----|------------
index.html | The root of the client side application. This is the `single page` of the SPA.
angularcrud.appcache | The application cache manifest. If using the Chrome browser, the URL `chrome://appcache-internals/` will display cached components with a link to remove them.


### web\js
AngularJS Controller and Services. These are the JavaScript files we create.

### web\lib
Third party libraries used by the SPA including AngularJS, Bootstrap, jQuery and localForage libraries.

### web\app\views
HTML templates injected into index.html for viewing a list of data, details of item and editing/deleting.

## Misc

### appcache
Caches components locally
Updated components are not ...



