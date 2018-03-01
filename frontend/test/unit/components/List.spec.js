import { shallow } from 'enzyme';
import { expect } from 'chai';

import React from 'react';
import List, { ListTitle } from '../../../src/ui/component/List';

const title = 'Awesome list'
const items = ['one', 'two', 'three'];
let props = { title, items };

describe('components', () => {
  describe('List', () => {
    it('should render three items', () => {
      const component = shallow(<List {...props} />);
      expect(component.find('li').length).to.equal(3);
    });

    it('should render a title', () => {
      const component = shallow(<List {...props} />);
      expect(component.find(ListTitle).dive().text()).to.equal('Awesome list');
    });
  });
});
