var gulp = require("gulp");
var sass = require('gulp-sass');
var cssimport = require("gulp-cssimport");
var options = {};

var watchScss = '';
var watchCss = '';

gulp.task("import", ['sass'], function () {
    gulp.src("./pages/**/*.css", { base: './pages/' })
        .pipe(cssimport(options))
        .pipe(gulp.dest("./pages/"));
});

function swallowError(error) {
    // If you want details of the error in the console
    console.error(error.toString())

    this.emit('end')
}

gulp.task('sass', function () {
    return gulp.src(['./pages/**/*.scss'], { base: './pages/' })
        .pipe(sass())
        .on('error', swallowError)
        .pipe(gulp.dest('./pages/'));
});

gulp.task("importOnce", ['sassOnce'], function () {
    gulp.src(watchCss, { base: './pages/' })
        .pipe(cssimport(options))
        .pipe(gulp.dest("./pages/"));
});

gulp.task('sassOnce', function () {
    return gulp.src([watchScss], { base: './pages/' })
        .pipe(sass())
        .on('error', swallowError)
        .pipe(gulp.dest('./pages/'));
});

gulp.task('watch', function () {
    watcher = [];

    var w1 = gulp.watch(['./pages/**/*.scss'], ['importOnce']);

    w1.on('change', function (e) {
        watchScss = e.path;
        watchCss = e.path.split('.scss')[0] + '.css';
    })

    watcher.push([w1]);
});

gulp.task('build', ['import']);

gulp.task('default', ['watch']);