const path = require('path');
const express = require('express');
const webpack = require('webpack');
const config = require('./webpack.dev.config');
const proxy = require('http-proxy-middleware');

const app = express();
const compiler = webpack(config);

app.use(require('connect-history-api-fallback')());
app.use(proxy('/api', { target: 'http://localhost:8080', changeOrigin: true }));

app.use(require('webpack-dev-middleware')(compiler));
app.use(require('webpack-hot-middleware')(compiler));

app.use(express.static('../backend/src/main/resources/static'));

app.listen(3000, 'localhost', err => {
  if (err) {
    console.log(err);
    return;
  }

  console.log('Listening at http://localhost:3000');
});
