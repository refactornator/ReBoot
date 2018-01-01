import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const HorizontalMenu = styled.div`
  box-sizing: border-box;
  width: 100%;
  white-space: nowrap;
`;

const MenuList = styled.ul`
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
  display: inline-block;
`;

const MenuItem = styled.li`
  position: relative;
  padding: 0;
  margin: 0;
  height: 100%;
  display: inline-block;
  *display: inline;
  zoom: 1;
  vertical-align: middle;
`;

const MenuLink = styled(Link)`
  display: block;
  text-decoration: none;
  white-space: nowrap;
  padding: 0.5em 1em;
`;

export default class Menu extends Component {
  render() {
    return (
      <HorizontalMenu>
        <MenuList role="menu">
          <MenuItem>
            <MenuLink to="/">Home</MenuLink>
          </MenuItem>
        </MenuList>
      </HorizontalMenu>
    );
  }
}
