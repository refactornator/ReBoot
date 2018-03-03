import React, { Component } from 'react';
import { connect } from 'react-redux';

import Menu from '../component/Menu';
import PersonInput from '../component/PersonInput';

import PersonList from './PersonList';


export class App extends Component {
  render() {
    return (
      <div id="application">
        <Menu />
        <PersonList />
        <PersonInput />
      </div>
    );
  }
}

export default connect(state => ({}), {})(App);
