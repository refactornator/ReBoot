import { shallow } from 'enzyme';
import { expect } from 'chai';

import React from 'react';
import AddPerson from "../../../src/ui/component/AddPerson";

describe('components', () => {
  describe('AddPerson', () => {

    it('should render two text input boxes', () => {
      const component = shallow(<AddPerson />);
      expect(component.find('[role="firstNameInput"]')).to.exist();
      expect(component.find('[role="lastNameInput"]')).to.exist();
    });
  });
});
