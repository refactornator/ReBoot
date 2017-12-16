import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';

const TopMenu = props => {
  return (
    <div className="pure-menu pure-menu-horizontal">
      <ul className="pure-menu-list">
        <li className="pure-menu-item">
          <Link to="/" className="pure-menu-link">
            Home
          </Link>
        </li>
      </ul>
    </div>
  );
};

export class App extends Component {
  render() {
    return (
      <div id="application">
        <TopMenu />
        {this.props.children}
      </div>
    );
  }
}

export default connect(state => ({}), {})(App);
