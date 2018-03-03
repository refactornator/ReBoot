import { shallow } from 'enzyme';
import { expect } from 'chai';

import PersonInput from '../../../src/ui/component/PersonInput';

import React from 'react';


describe('components', () => {
  describe('PersonInput', () => {
    it('renders firstname and lastname input boxes', () => {
      let personInput = shallow(<PersonInput/>);
      expect(personInput.find("input#firstName").exists()).to.equals(true);
      expect(personInput.find("input#lastName").exists()).to.equals(true);
    });

    it('renders an add button', () => {
      let personInput = shallow(<PersonInput/>);
      expect(personInput.find("button#addPerson").exists()).to.equals(true);
    });
  });
});
