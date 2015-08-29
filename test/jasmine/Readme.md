# Testing with Stand-Alone Jasmine

This directory contains unit tests using the stand-alone version of [Jasmine](<http://jasmine.github.io/>) testing framework. The tests run against the sample Timecard Review application written by Chris Camargo.

## Directory Structure

The `lib` directory contains the version of Jasmine (2.2) used in the tests.
The `spec` directory contains the Jasmine tests. The `index.html` file contains reference to the Jasmine css, JavaScript files, and application's
JavaScript files found in the application's `public_html` directory.

## Setup

Because stand-alone Jasmine (2.2) is included in the code, to run the test once, no additional setup is necessary. The application does not even have to be running. Just double-click the `index.html` file.

## Upgrading

Although the code here includes a version of Jasmine, Jasmine is constantly being upgraded. There are several ways of obtaining Jasmine on its own. It may be installed using `bower` or, alternatively, adding it to a project's `bower.json` file. 

The stand-alone version of Jasmine may also be downloaded directly from Jasmine's [releases page](<https://github.com/jasmine/jasmine/releases>) off [Jasmine's Github site](<https://github.com/jasmine/jasmine>).

## Continuous Running

While Jasmine could be run in a test runner, such as [Karma](<http://karma-runner.github.io/0.12/index.html>), it is possible to just run it in a
continuously updating web server, such as [live-server](<https://www.npmjs.com/package/live-server>). Live-server is a simple web-server that will constantly push updated files to a browser. There are advantages to running Jasmine in its own web server, such as getting the full Jasmine report as opposed to a one-line Karma report. Also, Karma requires complicated configuration (there is a set-up for it), whereas stand-alone Jasmine, just requires simple inclusion in the `index.html` file.

To install live-server, from the command line, run

  npm install -g live-server

Then, Jasmine stand-alone can be run from the command line. Since, Jasmine needs both project and test files, the web-server needs to be run from the top level directory of the project (i.e. to have access to both the `public_html` and `test/jasmine` directories). In the top level directory, there is a `.bat` file called `run-jasmine-test.bat` that will launch a continuous test of Jasmine stand-alone. The file only contains one line

  live-server --port=8181 --open=test/jasmine/
  
That tells live-server to run the web server on port 8181 (live-server's default is 8080, but other applications use that, or live-server could also be used to run the sample application. . .) and to start in the test/jasmine directory where it will find the `index.html` file.

## Jasmine Reporters

To report to the console (which will also report to the command line during a
PhantomJS run) or to create Junit XML style reports, it is necessary to include
the [Jasmine Reporters](https://github.com/larrymyers/jasmine-reporters)
libraries. This repository now includes those libraries in the `lib`
directory.

## Running from the Command Line via PhantomJS

PhantomJS is an application for running browser web tests outside the
browser in a "headless" manner. It is discussed more thoroughly in the
[gulp directory](../../gulp/).

Besides running in the browser or in gulp (see below), it is possible to run
Jasmine tests from the command line using PhantomJS. This can be done in
this directory using the following command.

`
phantomjs run-jasmine2.js index.html
`

The run-jasmine2.js file can be found in the [PhantomJS examples](https://github.com/ariya/phantomjs/tree/master/examples).

## Reporting to gulp

In order for the Jasmine Reporters to report properly, it is necessary to include
the [Jasmine 2 JUnit package](https://github.com/sandermak/jasmine2-junit) in the `index.html` file.
The following lines 

`
  <script src="lib/jasmine2-junit.js"></script>
  <script src="lib/boot.js"></script>
`

allow the tests to be reported to gulp. The combination of the Jasmine Reports
and the Jasmine 2 JUnit package will also allow the tests to be reported to
Jenkins.


