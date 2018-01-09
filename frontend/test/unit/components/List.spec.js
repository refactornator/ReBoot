import { shallow } from 'enzyme';
import { expect } from 'chai';

import React from 'react';
import List from '../../../src/ui/component/List';

const items = ['one', 'two', 'three'];
let props = { items };

describe('components', () => {
  describe('List', () => {
    it('should render three items', () => {
      const component = shallow(<List {...props} />);
      expect(component.find('li').length).to.equal(3);
    });
  });
});
