import React, { Component } from 'react';
import { connect } from 'react-redux';

import Menu from '../component/Menu';

export class App extends Component {
  render() {
    return (
      <div id="application">
        <Menu />
        {this.props.children}
      </div>
    );
  }
}

export default connect(state => ({}), {})(App);
