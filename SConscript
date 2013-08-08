Import('env')

env.install(env.compileAndJar('jstesting.jar', 'testing',
        libs=[env['CLOJURE']],
    ))
# env.install(env.compileJs('testing.js', 'testing',
#         jarlibs=['$BUILD_DIR/jstesting.jar'],
#         options={":optimizations": ":simple", ":pretty-print": True}
#     ))
