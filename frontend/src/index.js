import ReactDOM from 'react-dom';
import React from 'react';

import { Provider } from 'react-redux';
import { createStore, combineReducers, applyMiddleware, bindActionCreators } from 'redux';

import { Route } from 'react-router-dom';
import createHistory from 'history/createBrowserHistory';
import { ConnectedRouter, routerReducer, routerMiddleware } from 'react-router-redux';

import { ApolloProvider } from 'react-apollo';
import { ApolloClient } from 'apollo-client';
import { HttpLink } from 'apollo-link-http';
import { InMemoryCache } from 'apollo-cache-inmemory';

import { AppContainer } from 'react-hot-loader';

import App from './ui/container/App';

const client = new ApolloClient({
  link: new HttpLink(),
  cache: new InMemoryCache()
});

const history = createHistory();
const middleware =
  process.env.NODE_ENV === 'development'
    ? [routerMiddleware(history)]
    : [routerMiddleware(history)];

const store = createStore(
  combineReducers({
    router: routerReducer
  }),
  {},
  applyMiddleware(...middleware)
);

const actions = bindActionCreators({}, store.dispatch);

const render = Component => {
  ReactDOM.render(
    <AppContainer>
      <Provider store={store}>
        <ApolloProvider client={client}>
          <ConnectedRouter history={history}>
            <Route exact path="/" component={App} />
          </ConnectedRouter>
        </ApolloProvider>
      </Provider>
    </AppContainer>,
    document.getElementById('root')
  );
};

render(App);

// Webpack Hot Module Replacement API
if (module.hot) {
  module.hot.accept('./ui/container/App', () => {
    render(App);
  });
}
