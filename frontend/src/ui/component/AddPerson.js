import React, { Component } from 'react';
import styled from 'styled-components';

const Container = styled.div``;

export const ListTitle = styled.h2`
  margin-left: 16px;
`;

export default () => {
  return (
    <Container>
      <input type="text"></input>
      <input type="text"></input>
    </Container>
  )};
