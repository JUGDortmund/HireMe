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
ngmin = require('gulp-ngmin')

generatedFileWarning = '/* Warning! This is a generated file. Do not modify. Use Gulp task instead */\n'
distFolder = 'src/main/java/app/dist'

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
              title: 'Production Build'
              message: 'Done'

gulp.task 'watch', ->
  gulp.start('default')
  gulp.watch 'src/main/java/app/**/*.coffee', ['build-js']
  gulp.watch 'src/main/java/app/**/*.hs', ['build-js']
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
  gulp.src(['src/main/java/assets/css/**/*.css']).pipe(concat('main.css')).pipe(header(generatedFileWarning)).pipe gulp.dest(distFolder)
gulp.task 'uglify-css', ->
  gulp.src(['src/main/java/assets/css/**/*.css'])
  .pipe(concat('main.min.css'))
  .pipe(header(generatedFileWarning))
  .pipe(uglify())
  .pipe gulp.dest(distFolder)

