var path = require('path');
const webpack = require('webpack');

module.exports = {
    entry: {
        "myec-home": './src/main/webapp/assets/js/myec-home.js',
        "myec-home.min": './src/main/webapp/assets/js/myec-home.js',
        "myec-product-list": './src/main/webapp/assets/js/myec-product-list.js',
        "myec-product-list.min": './src/main/webapp/assets/js/myec-product-list.js',
        "myec-table-test": './src/main/webapp/assets/js/myec-table-test.js',
    },
    output: {
        filename: '[name].bundle.js',
        path: path.resolve(__dirname, 'target/www/js'),
        publicPath: "../../../target/www/js/",
        library: 'myec'
    },
    module: {
        loaders: [
            { test: /\.jsx?$/, exclude: /(node_modules|bower_components)/, loader: 'babel' },
			{ test: /\.css$/, loader: 'style-loader!css-loader' },
			{ test: /\.eot(\?v=\d+\.\d+\.\d+)?$/, loader: "file" },
			{ test: /\.(woff|woff2)$/, loader:"url?prefix=font/&limit=5000" },
			{ test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/, loader: "url?limit=10000&mimetype=application/octet-stream" },
			{ test: /\.svg(\?v=\d+\.\d+\.\d+)?$/, loader: "url?limit=10000&mimetype=image/svg+xml" },
            { test: /\.(jpg|png|gif)$/, loader: 'file-loader!url-loader', options: {name: '[path][name].[hash].[ext]', limit: 25000}}
        ]
    },
    plugins: [
            new webpack.ProvidePlugin({
                $: "jquery",
                jQuery: "jquery",
                "window.jQuery": "jquery",
                "window.$": "jquery"
            })
        ]
};

