const path = require('path');

// noinspection JSUnusedGlobalSymbols
module.exports = {
    runtimeCompiler: true,
    outputDir: '../src/main/webapp/assets/admin/',
    // assetsDir: 'assets',
    filenameHashing: false,
    configureWebpack: {
        devServer: {
            proxy: {
                "/api*": {
                    target: "http://localhost:8080//demo_war/",
                }
            }
        },
        resolve: {
            alias: {
                '~': path.resolve(__dirname, 'src/')
            }
        }
    },
    chainWebpack: (config) => {
        if (process.env.NODE_ENV === 'production') {
            config.plugin('html').tap((opts) => {
                opts[0].filename = '../../home.html';
                return opts;
            });
        }
        const cssRule = config.module.rule('css');
        cssRule
            .use('sass-loader')
            .loader('sass-loader')
            .tap(options => {
                // modify the options...
                return options
            });
    },
};
