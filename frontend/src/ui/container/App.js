import React, { Component } from 'react';
import { connect } from 'react-redux';

import Menu from '../component/Menu';
import PersonList from './PersonList';

export class App extends Component {
  render() {
    return (
      <div id="application">
        <Menu />

        <PersonList />
      </div>
    );
  }
}

export default connect(state => ({}), {})(App);
