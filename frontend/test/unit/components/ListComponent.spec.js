import { shallow } from 'enzyme';
import { expect } from 'chai';

import React from 'react';
import ListComponent from '../../../src/ui/component/ListComponent';

const items = ['one', 'two', 'three'];
let props = { items };

describe('components', () => {
  describe('ListComponent', () => {
    it('should render three items', () => {
      const component = shallow(<ListComponent {...props} />);
      expect(component.find('li').length).to.equal(3);
    });
  });
});
