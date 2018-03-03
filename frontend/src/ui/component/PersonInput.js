import React, {Component} from 'react';
import {graphql} from 'react-apollo';
import gql from 'graphql-tag';

export default class PersonInput extends Component {
  render() {
    return (
      <div>
        <input id="firstName" type="text"/>
        <input id="lastName" type="text"/>
        <button id="addPerson">Add</button>
      </div>
    );
  }
}

