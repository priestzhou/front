Import('env')

env.install(env.compileAndJar('jstesting.jar', 'testing',
        libs=[env['CLOJURE']],
    ))

stmainjs = env.install(env.compileJs('front_st_samplecase.js', 'smoketest',
        options={":optimizations": ":simple", ":pretty-print": True}
        # :optimizations must be :simple to avoid optimization mistakes
    ))

stjar = env.install(env.compileAndJar('front_smoketest.jar', 'smoketest',
        libs=[env['CLOJURE'],
            env.File('$EXTLIB/ring-1.1.8.jar'),
            env.File('$EXTLIB/ring-jetty-adapter-1.1.8.jar'),
            env.File('$EXTLIB/data.json-0.2.2.jar'),
            env.File('$BUILD_DIR/testing.jar'),
            env.File('$BUILD_DIR/utilities.jar'),
        ],
        standalone=True, manifest={'Main-Class': 'smoketest.main'}
    ))

env.Depends(stjar, stmainjs)
