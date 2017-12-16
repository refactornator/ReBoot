import ReactDOM from 'react-dom';
import React from 'react';
import { Route } from 'react-router';
import { Provider } from 'react-redux';
import { createStore, combineReducers, applyMiddleware, bindActionCreators } from 'redux';
import createHistory from 'history/createBrowserHistory';
import { ConnectedRouter, routerReducer, routerMiddleware } from 'react-router-redux';
import App from './ui/container/App';
import SimpleListComponent from './ui/container/SimpleListComponent';

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

ReactDOM.render(
  <Provider store={store}>
    <ConnectedRouter history={history}>
      <div>
        <Route exact path="/" component={App} />
      </div>
    </ConnectedRouter>
  </Provider>,
  document.getElementById('root')
);
