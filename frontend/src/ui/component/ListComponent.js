import React, { Component } from 'react';

export default class ListComponent extends Component {
  render() {
    const { items } = this.props;

    return (
      <div>
        <h2>Data:</h2>
        <ul>{items.map((item, index) => <li key={index}>{item}</li>)}</ul>
      </div>
    );
  }
}
