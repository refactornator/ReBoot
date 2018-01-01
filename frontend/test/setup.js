import puppeteer from 'puppeteer';
import { expect } from 'chai';
import _ from 'lodash';

require('babel-core/register');
require('babel-polyfill');

const globalVariables = _.pick(global, ['browser', 'expect']);

// puppeteer options
const opts = {
  headless: true,
  timeout: 10000
};

// expose variables
before(done => {
  global.expect = expect;

  puppeteer.launch(opts).then(browser => {
    global.browser = browser;
    done();
  });
});

// close browser and reset global variables
after(() => {
  browser.close();

  global.browser = globalVariables.browser;
  global.expect = globalVariables.expect;
});
