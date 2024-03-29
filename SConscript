Import('env')

env.install(env.compileAndJar('jstesting.jar', 'testing',
        libs=[env['CLOJURE']],
    ))
env.install(env.compileAndJar('jsutilities.jar', 'utilities',
        libs=[env['CLOJURE']],
    ))

stmainjs = env.install(env.compileJs('front_st_samplecase.js', 'smoketest',
    options={":optimizations": ":simple", ":pretty-print": True}
    # :optimizations must be :simple to avoid munging mistakes about phantomjs
))

st_samplecase_js = env.compileJs('samplecase.js', 'sample_st_case',
    libs=[env.File('$EXTLIB/dommy-0.1.2-SNAPSHOT.jar'),
    ],
    options={":optimizations": ":advanced"}
)

stjar = env.install(env.compileAndJar('front_smoketest.jar', 'smoketest',
    libs=[env['CLOJURE'],
        env.File('$EXTLIB/ring-1.1.8.jar'),
        env.File('$EXTLIB/ring-jetty-adapter-1.1.8.jar'),
        env.File('$EXTLIB/data.json-0.2.2.jar'),
        env.File('$BUILD_DIR/testing.jar'),
        env.File('$BUILD_DIR/utilities.jar'),
    ],
    install={env.File('sample_st_case/samplecase.html'): '@/resources',
        st_samplecase_js: '@/resources',
    },
    standalone=True, manifest={'Main-Class': 'smoketest.main'}
))

env.Depends(stjar, stmainjs)

lm_js = env.compileJs('log_monitor.js', 'log_monitor',
    libs=[env.File('$BUILD_DIR/jsutilities.jar'),
        env.File('$EXTLIB/cljs-ajax-0.1.6.jar'),
    ],
    options={":optimizations": ":simple", ":pretty-print": True,
        ":externs": ["\"%s\"" % (env.File('resources/js/goog.extern.js').abspath),
        ],
    }
)

mock_server = env.install(env.compileAndJar('lm_mock_server.jar', 'log_monitor',
    libs=[env['CLOJURE'],
        env.File('$EXTLIB/ring-1.1.8.jar'),
        env.File('$EXTLIB/ring-jetty-adapter-1.1.8.jar'),
        env.File('$EXTLIB/data.json-0.2.2.jar'),
        env.File('$BUILD_DIR/utilities.jar'),
    ],
    install={lm_js: '@/resources/js',
        env.File('$EXTLIB/highcharts.js'): '@/resources/js',
        env.File('$EXTLIB/jquery-1.10.2.min.js'): '@/resources/js',
        env.File('resources/js/jquery-ui.js'): '@/resources/js',
        env.File('resources/js/jquery.loadmask.min.js'): '@/resources/js',
        env.File('resources/js/bootstrap.min.js'): '@/resources/js',
        env.File('resources/js/jquery.pagination.js'): '@/resources/js',

        env.File('log_monitor/index.html'): '@/resources/',

        env.File('resources/css/bootstrap.css'): '@/resources/css',
        env.File('resources/css/index.css'): '@/resources/css',
        env.File('resources/css/jquery-ui.css'): '@/resources/css',
        env.File('resources/css/jquery.loadmask.css'): '@/resources/css',

        env.File('resources/image/bar.png'): '@/resources/image',
        env.File('resources/image/go_btn.png'): '@/resources/image',
        env.File('resources/image/icons_sprite.png'): '@/resources/image',
        env.File('resources/image/loader.gif'): '@/resources/image',
        env.File('resources/image/logo.png'): '@/resources/image',
        env.File('resources/image/search_bar.png'): '@/resources/image',
        env.File('resources/image/shadow_soft.png'): '@/resources/image',
        env.File('resources/image/splIcons.gif'): '@/resources/image',
        env.File('resources/image/sprite_button_icons.png'): '@/resources/image',
    },
    standalone=True, manifest={'Main-Class': 'log_monitor.mock_server'},
))

lq_js = env.compileJs('log_query.js', 'log_query',
    libs=[env.File('$BUILD_DIR/jsutilities.jar'),
        env.File('$EXTLIB/cljs-ajax-0.1.6.jar'),
    ],
    options={":optimizations": ":advanced",
        ":externs": ["\"%s\"" % (env.File('resources/js/goog.extern.js').abspath),
        ],
    }
)

mock_server = env.install(env.compileAndJar('lq_mock_server.jar', 'log_query',
    libs=[env['CLOJURE'],
        env.File('$EXTLIB/ring-1.1.8.jar'),
        env.File('$EXTLIB/ring-jetty-adapter-1.1.8.jar'),
        env.File('$EXTLIB/data.json-0.2.2.jar'),
        env.File('$BUILD_DIR/utilities.jar'),
    ],
    install={lq_js: '@/resources/js',
        env.File('$EXTLIB/highcharts.js'): '@/resources/js',
        env.File('$EXTLIB/jquery-1.10.2.min.js'): '@/resources/js',
        env.File('resources/js/jquery-ui.js'): '@/resources/js',
        env.File('resources/js/jquery.loadmask.min.js'): '@/resources/js',
        env.File('resources/js/bootstrap.min.js'): '@/resources/js',
        env.File('resources/js/jquery.pagination.js'): '@/resources/js',
        env.File('resources/js/jscal2.js'): '@/resources/js',
        env.File('resources/js/cn.js'): '@/resources/js',

        env.File('log_query/index.html'): '@/resources/',

        env.File('log_query/index.css'): '@/resources/css',
        env.File('resources/css/bootstrap.css'): '@/resources/css',
        env.File('resources/css/jquery-ui.css'): '@/resources/css',
        env.File('resources/css/jquery.loadmask.css'): '@/resources/css',
        env.File('resources/css/jscal2.css'): '@/resources/css',

        env.File('resources/image/bar.png'): '@/resources/image',
        env.File('resources/image/go_btn.png'): '@/resources/image',
        env.File('resources/image/icons_sprite.png'): '@/resources/image',
        env.File('resources/image/loader.gif'): '@/resources/image',
        env.File('resources/image/logo.png'): '@/resources/image',
        env.File('resources/image/sprite_button_icons.png'): '@/resources/image',
        env.File('resources/image/drop-down.gif'): '@/resources/image',
        env.File('resources/image/drop-up.gif'): '@/resources/image',
        env.File('resources/image/nav-left.gif'): '@/resources/image',
        env.File('resources/image/nav-right.gif'): '@/resources/image',
        env.File('resources/image/nav-left-x2.gif'): '@/resources/image',
        env.File('resources/image/nav-right-x2.gif'): '@/resources/image',
        env.File('resources/image/time-down.png'): '@/resources/image',
        env.File('resources/image/time-up.png'): '@/resources/image',
    },
    standalone=True, manifest={'Main-Class': 'log_query.mock_server'},
))
