import React from 'react';

import { storiesOf } from '@storybook/react';
import StoryRouter from 'storybook-router';
import { action } from '@storybook/addon-actions';
import { linkTo } from '@storybook/addon-links';

import { Welcome } from '@storybook/react/demo';

import ListComponent from '../src/ui/component/ListComponent';
import Menu from '../src/ui/component/Menu';

storiesOf('Welcome', module).add('to Storybook', () => <Welcome showApp={linkTo('Button')} />);

storiesOf('Menu', module)
  .addDecorator(StoryRouter())
  .add('by default', () => <Menu />);

storiesOf('List', module)
  .addDecorator(StoryRouter())
  .add('with a title and list items', () => (
    <ListComponent title="Stuff" items={['one', 'two', 'three']} />
  ));
