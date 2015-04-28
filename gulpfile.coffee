gulp = require('gulp')
require('coffee-script/register')
concat = require('gulp-concat')
notify = require('gulp-notify')
header = require('gulp-header')
uglify = require('gulp-uglify')
notifier = require('node-notifier')
coffee = require('gulp-coffee')
gulpif = require('gulp-if')
watch = require('gulp-watch')
livereload = require('gulp-livereload')
less = require('gulp-less')
ngmin = require('gulp-ngmin')
plumber = require('gulp-plumber')
uglifycss = require('gulp-uglifycss')

generatedFileWarning = '/* Warning! This is a generated file. Do not modify. Use Gulp task instead */\n'
distFolder = 'src/main/java/assets/dist'

jsBaseBuild =
  gulp.src([
             'src/main/java/app/**/*.coffee'
             'src/main/java/app/**/*.js'
             '!src/main/java/app/dist/**'
           ])
  .pipe(gulpif(/[.]coffee$/, coffee({bare: true})))

gulp.task 'default', [
  'build-js'
  'build-css'
], ->
            notifier.notify
              title: 'Finished Resource Generation'
              message: 'Done'

gulp.task 'watch', ->
  gulp.start('default')
  watch 'src/main/java/app/**/*.coffee', ->
    gulp.start 'build-js'
  watch 'src/main/java/app/**/*.js', ->
    gulp.start 'build-js'
  watch 'src/main/java/assets/css/**/*.css', ->
    gulp.start 'build-css'
  watch 'src/main/java/assets/less/**/*.less', ->
    gulp.start 'build-css'
  livereload.listen()
  gulp.watch([distFolder]).on 'change', livereload.changed

gulp.task 'build-js', [
  'concat-js'
  'uglify-js'
], ->
gulp.task 'concat-js', ->
  jsBaseBuild
  .pipe(concat('main.js'))
  .pipe(header(generatedFileWarning))
  .pipe gulp.dest(distFolder)
gulp.task 'uglify-js', ->
  jsBaseBuild
  .pipe(concat('main.min.js'))
  .pipe(ngmin())
  .pipe(uglify())
  .pipe(header(generatedFileWarning))
  .pipe gulp.dest(distFolder)
gulp.task 'build-css', [
  'concat-css'
  'uglify-css'
], ->
gulp.task 'concat-css', ->
  gulp.src(['src/main/java/assets/css/**/*.css'
    'src/main/java/assets/less/**/*.less'])
  .pipe(gulpif(/[.]less$/,less()))
  .pipe(concat('main.css'))
  .pipe(header(generatedFileWarning))
  .pipe gulp.dest(distFolder)
gulp.task 'uglify-css', ->
  gulp.src([distFolder + '/main.css'
    'src/main/java/assets/less/style.less'])
  .pipe(plumber())
  .pipe(gulpif(/[.]less$/,less()))
  .pipe(concat('main.min.css'))
  .pipe(uglifycss())
  .pipe gulp.dest(distFolder)

