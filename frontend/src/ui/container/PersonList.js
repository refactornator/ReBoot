import { graphql } from 'react-apollo';
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

export default graphql(PeopleQuery, {
  props: ({ data: { people = [] } }) => {
    return {
      title: 'People',
      items: people.map(person => `${person.firstName || ''} ${person.lastName || ''}`)
    };
  }
})(List);
