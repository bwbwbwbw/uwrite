var gulp          = require('gulp'),
    stylus        = require('gulp-stylus'),
    autoprefixer  = require('gulp-autoprefixer'),
    plumber       = require('gulp-plumber'),
    watch         = require('gulp-watch');

gulp.task('default', function () {
  return gulp
    .src('src/main/webapp/resources/css/uwrite.styl')
    .pipe(watch('src/main/webapp/resources/css/**/*.styl', function (file) {
      console.log('Changed: %s', file.path);
    }))
    .pipe(plumber())
    .pipe(stylus())
    .pipe(autoprefixer())
    .pipe(gulp.dest('./src/main/webapp/resources/css/'));
});