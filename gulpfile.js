/* global require */

// Timecard Review: gulpfile.js

var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var cssmin = require('gulp-cssmin');
var gulpFilter = require('gulp-filter');
var concatCss = require('gulp-concat-css');
var basename = require('gulp-css-url-basename');
var protractor = require("gulp-protractor").protractor;
var karma = require('gulp-karma');

// All of our application .js files.
var jsAppFiles = [
   '../web/controllers/timecardReview.js',
   '../web/controllers/*.js',
   '../web/services/*.js',
   '../web/directives/*.js'
];

// All of our library .js files.
var jsLibFiles = [
   '../web/lib/angular.js',
   '../web/lib/jquery.js',
   '../web/lib/bootstrap.js',
   '../web/lib/angular-animate.js',
   '../web/lib/angular-route.js',
   '../web/lib/angular-messages.js',
   '../web/lib/angular-sanitize.js',
   '../web/lib/moment.js',
   '../web/lib/ui-bootstrap-tpls-0.13.0.js',
   '../web/lib/toaster.js',
   '../web/lib/angular-busy.js',
   '../web/lib/ui-grid.js',
   '../web/lib/pdfmake.js',
   '../web/lib/vfs_fonts.js'
];

// All of our .css files (includes Bootstrap extensions).
var cssFiles = [
    '../web/css/bootstrap.css',
    '../web/css/app.css',
    '../web/css/toaster.css',
    '../web/css/angular-busy.css',
    '../web/css/ui-grid.css'
];

// All of our unit test files.
var unitTestSpecFiles = [
    '../test/unit/services/*Test.js'
];

// Concatenate all of our application .js files (Debug mode).
gulp.task('debug-app-js', function() {
    return gulp.src(jsAppFiles)                             // Add our custom .js files
               .pipe(concat('TimecardReviewApp.debug.js'))  // Concatenate all .js files
               .pipe(gulp.dest('../web/dist'));             // Put it in our dist folder
});

// Concatenate and minify all of our application .js files (Release mode).
gulp.task('release-app-js', function() {
    return gulp.src(jsAppFiles)                          // Add our custom .js files
               .pipe(concat('TimecardReviewApp.min.js')) // Concatenate all .js files
               .pipe(uglify())                           // Minify all .js files
               .pipe(gulp.dest('../web/dist'));          // Put it in our dist folder
});

// Concatenate all of our application .js files (Debug mode).
gulp.task('debug-libs-js', function() {
    return gulp.src(jsLibFiles)                              // Add our .js files
               .pipe(concat('TimecardReviewLibs.debug.js'))  // Concatenate all .js files
               .pipe(gulp.dest('../web/dist'));              // Put it in our dist folder
});

// Concatenate and minify all of our application .js files (Release mode).
gulp.task('release-libs-js', function() {
    return gulp.src(jsLibFiles)                              // Add our .js files
               .pipe(concat('TimecardReviewLibs.debug.js'))  // Concatenate all .js files
               .pipe(uglify())                               // Minify all .js files
               .pipe(gulp.dest('../web/dist'));              // Put it in our dist folder
});

// Concatenate all of our CSS files (Debug mode).
gulp.task('debug-css', function () {
    return gulp.src(cssFiles)                               // Get our css files (by directory and/or file name)
               .pipe(gulpFilter('**/*.css'))                // Make sure we have just .css files (for directory globbing)
               .pipe(basename({prefix: '../assets'}))       // Add '../assets' base name to CSS URLs (all images and fonts must be here)
               .pipe(concatCss('TimecardReview.debug.css')) // Concatenate all .css files.
               .pipe(gulp.dest('../web/dist'));             // Put it with our other Bootstrap .css files.
});

// Concatenate and minify all of our CSS files (Release mode).
gulp.task('release-css', function () {
    return gulp.src(cssFiles)                             // Get our css files (by directory and/or file name)
               .pipe(gulpFilter('**/*.css'))              // Make sure we have just .css files (for directory globbing)
               .pipe(basename({prefix: '../assets'}))     // Add '../assets' base name to CSS URLs (all images and fonts must be here)
               .pipe(concatCss('TimecardReview.min.css')) // Concatenate all .css files.
               .pipe(cssmin())                            // Minify all .css files.
               .pipe(gulp.dest('../web/dist'));           // Put it with our other Bootstrap .css files.
});

// Run our Protractor E2E tests.
gulp.task('e2e-test', function() {
    return gulp.src(['../test/e2e/**/spec.*.js']) // Pass in our spec files.
               .pipe(protractor({
                        seleniumServerJar: "node_modules/protractor/selenium/selenium-server-standalone-2.45.0.jar", // Local location of Selenium.
                        configFile: "../test/e2e/config.js", // Our Protractor config file.
                        args: ['--baseUrl', 'http://localhost:7001'] // Test local for now. Can be switched here if needed.
                }))
               .on('error', function(e) { throw e; });
});

// Run our Karma/Jasmine Unit tests.
gulp.task('unit-test', function() {
    return gulp.src(unitTestSpecFiles)
               .pipe(karma({
                    configFile: '../test/unit/karma.conf.js',
                    action: 'run'
               }))
               .on('error', function(e) { throw e; });
});

// Debug build (call 'gulp' to run). Default for now. No tests.
gulp.task('default', ['debug-app-js', 'debug-libs-js', 'debug-css']);

// Debug build with all tests (call 'gulp debug-test').
gulp.task('debug-test-all', ['default', 'e2e-test', 'unit-test']);

// Debug build with e2e tests only (call 'gulp debug-test-e2e').
gulp.task('debug-test-e2e', ['default', 'e2e-test']);

// Debug build with unit tests only (call 'gulp debug-test-unit').
gulp.task('debug-test-unit', ['default', 'unit-test']);

// Release build (call 'gulp release' to run). No tests.
gulp.task('release', ['release-app-js', 'release-libs-js', 'release-css']);
