import React, { Component } from 'react';
import { Query } from 'react-apollo';
import gql from 'graphql-tag';

import List from '../component/List';

const PeopleQuery = gql`
  {
    people {
      id
      firstName
      lastName
    }
  }
`;

export default () => (
  <Query query={PeopleQuery}>
    {({ data: { people = [] } }) => (
      <List
        title="People"
        items={people.map(person => `${person.firstName || ''} ${person.lastName || ''}`)}
      />
    )}
  </Query>
);
