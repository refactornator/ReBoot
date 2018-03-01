import React, { Component } from 'react';
import styled from 'styled-components';

const Container = styled.div``;

export const ListTitle = styled.h2`
  margin-left: 16px;
`;

export default ({ title, items }) => {
  return (
  <Container>
    <ListTitle>{title}</ListTitle>
    <ul role="list">{items.map((item, index) => <li key={index}>{item}</li>)}</ul>
  </Container>
)};
