Import('env')

env.install(env.compileAndJar('jstesting.jar', 'testing',
        libs=[env['CLOJURE']],
    ))

stmainjs = env.install(env.compileJs('front_st_samplecase.js', 'smoketest',
    options={":optimizations": ":simple", ":pretty-print": True}
    # :optimizations must be :simple to avoid optimization mistakes
))

st_samplecase_js = env.compileJs('samplecase.js', 'sample_st_case',
    options={":optimizations": ":simple", ":pretty-print": True}
)

stjar = env.install(env.compileAndJar('front_smoketest.jar', 'smoketest',
        libs=[env['CLOJURE'],
            env.File('$EXTLIB/ring-1.1.8.jar'),
            env.File('$EXTLIB/ring-jetty-adapter-1.1.8.jar'),
            env.File('$EXTLIB/data.json-0.2.2.jar'),
            env.File('$BUILD_DIR/testing.jar'),
            env.File('$BUILD_DIR/utilities.jar'),
            env.File('$EXTLIB/domina-1.0.1.jar'),
        ],
        install={env.File('sample_st_case/samplecase.html'): '/resources',
            st_samplecase_js: '/resources',
        },
        standalone=True, manifest={'Main-Class': 'smoketest.main'}
    ))

env.Depends(stjar, stmainjs)
