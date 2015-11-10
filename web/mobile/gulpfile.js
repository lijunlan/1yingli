var gulp = require('gulp');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var gzip = require('gulp-gzip');

var wwwroot = './';
var paths = {
    scripts: [
        "bower_components/jquery/dist/jquery.js",
        "bower_components/jquery-json/dist/jquery.json.min.js",
        "bower_components/fastclick/lib/fastclick.js",
        "bower_components/angular/angular.js",
        "bower_components/angular-route/angular-route.js",
        "bower_components/angular-touch/angular-touch.js",
        "js/common.js",
        "pages/**/*.js",
        "service/*.js",
        "js/sha1.js",
        "js/wx.js"
    ]
};

gulp.task("scripts", function () {
    gulp.src(paths.scripts)
        .pipe(concat('app.js'))
        .pipe(uglify())
        //.pipe(gzip())
        .pipe(gulp.dest(wwwroot))
});

gulp.task("default", ["scripts", "watch"], function () {
});

gulp.task("watch", function () {
    gulp.watch(paths.scripts, ["scripts"]);
});
