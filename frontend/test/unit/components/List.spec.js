import { shallow } from 'enzyme';
import { expect } from 'chai';

import React from 'react';
import List, { ListTitle } from '../../../src/ui/component/List';

const items = ['one', 'two', 'three'];
const title = "Awesome!!"
let props = { title, items };

describe('components', () => {
  describe('List', () => {
    it('should render three items', () => {
      const component = shallow(<List {...props} />);
      expect(component.find('li').length).to.equal(3);
    });

    it("should render the title", () => {
      const component = shallow(<List title={title}  />);
      expect(component.find(ListTitle).dive().text()).to.equal(title);
    })
  });
});
