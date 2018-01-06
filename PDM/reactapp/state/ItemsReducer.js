/**
 * User reducer, uses the object trick implemented in ActionTypes
 */
import ActionTypes from './ActionTypes';
import {defaultItems} from "./Records";


class ItemsReducer {
    static reduce(state = defaultItems, action) {
        if (ItemsReducer[action.type]) {
            // if tit's defined, delegate. basically a more elegant switch
            return ItemsReducer[action.type](state, action);
        } else {
            return state;
        }
    }

    /**
     * Adds a new item to the list
     * @param state
     * @param item
     */
    static [ActionTypes.ADD_ITEM] = (state, {item}) => ({
        ...state,
        entities: state.entities.concat([item])
    });

    /**
     * Updates the given item with new values
     * The item is uniquely identified by listId and id
     * @param state
     * @param item
     */
    static [ActionTypes.UPDATE_ITEM] = (state, {item}) => ({
        ...state,
        entities: state.entities.map(
            // if it's that item, update its values
            it => (it.listId === item.listId && it.id === item.id) ?
                item : it
        )
    });

    /**
     * Sets the fetch flag to true
     * @param state
     */
    static [ActionTypes.ITEM_FETCH_START] = (state) => ({
        ...state,
        isFetching: true
    });

    /**
     * Sets the items to the the new given set
     * @param state
     * @param items
     */
    static [ActionTypes.SET_ITEMS] = (state, {items}) => ({
        ...state,
        entities: items
    });

    /**
     * Sets the fetch flag to false
     * @param state
     */
    static [ActionTypes.ITEM_FETCH_STOP] = (state, {}) => ({
        ...state,
        isFetching: false
    });

    /**
     * Replaces the entire set of entities with the new one
     * @param state
     */
    static [ActionTypes.ITEM_FETCH_START] = (state, {}) => ({
        ...state,
        isFetching: true
    });


}

export default ItemsReducer.reduce;