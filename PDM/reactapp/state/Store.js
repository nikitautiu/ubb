/**
 * Global reducer combining the user and list one
 * t modify separate parts of the subtree.
 * Very useful
 */
import { combineReducers, createStore, applyMiddleware } from 'redux';
import createLogger from 'redux-logger'
import thunkMiddleware from 'redux-thunk';
import CurrentUserReducer from './CurrentUserReducer';
import ItemsReducer from "./ItemsReducer";

const loggerMiddleware = createLogger();


export default createStore(
    combineReducers({
        currentUser: CurrentUserReducer,
        items: ItemsReducer,
    }),
    applyMiddleware(
        thunkMiddleware,
        loggerMiddleware // neat middleware that logs actions
    )
);