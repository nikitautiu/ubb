/**
 * User reducer, uses the object trick implemented in ActionTypes
 */
import ActionTypes from './ActionTypes';
import {defaultCurrentUser} from "./Records";


class CurrentUserReducer {
    static reduce(state = defaultCurrentUser, action) {
        if (CurrentUserReducer[action.type]) {
            // if tit's defined, delegate. basically a more elegant switch
            return CurrentUserReducer[action.type](state, action);
        } else {
            return state;
        }
    }

    static [ActionTypes.LOG_IN_SUCCESS](state, action) {
        return {
            ...state,
            isFetching: false,
            isFailed: false,
            user: action.user
        }; // overwrite the user state
    }

    static [ActionTypes.LOG_IN_FAIL](state, action) {
        return {
            ...state,
            isFetching: false,
            isFailed: true
        }; // overwrite the user state
    }

    static [ActionTypes.LOG_IN_START](state, action) {
        return {
            ...state,
            isFetching: true
        }
    }

    static [ActionTypes.RESET](state, action) {
        return new User();
    }
}

export default CurrentUserReducer.reduce;