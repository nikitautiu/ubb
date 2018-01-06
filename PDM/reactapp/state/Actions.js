/**
 * Defines constructors for the different types of actions
 */
import ActionTypes from "./ActionTypes";
import {itemAddCall, itemGetCall, itemUpdateCall, loginCall} from "../Api/Api";
import {saveStateToStorage} from "./LocalStorage";

/* Action creators */
export default Actions = class  {
    static logInSuccess = (user) => ({
        type: ActionTypes.LOG_IN_SUCCESS,
        user
    });

    static logInFail = () => ({
            type: ActionTypes.LOG_IN_FAIL,
        }
    );

    static logInStart = () => ({
        type: ActionTypes.LOG_IN_START
    });

    static addItem = (newItem) => ({
        type: ActionTypes.ADD_ITEM,
        item: newItem
    });

    static updateItem = (item) => ({
        type: ActionTypes.UPDATE_ITEM,
        item: item
    });

    static setItemFetching = (flag) => ({
       type: flag ? ActionTypes.ITEM_FETCH_START : ActionTypes.ITEM_FETCH_STOP
    });

    static setItems = (newItems) => ({
        type: ActionTypes.SET_ITEMS,
        items: newItems
    });

    /** Try logging in in the background given a username
     * and password. On success sets the needed store values.
     * @param username
     * @param password
     * @returns {function(*)}
     */
    static asyncLoginUser = ({username, password}) => {
        // using thunk to enable
        // side effects such as api calls

        return dispatch => {
            dispatch(Actions.logInStart());

            // defining the success and error handlers
            // chain a load after login
            const onSuccess = token => dispatch(Actions.logInSuccess({
                authToken: token,
                name: username
            })).then(() => dispatch(Actions.asyncGetItems({id: 1})));
            const onFail = () => dispatch(Actions.logInFail());

            loginCall({username, password, onSuccess, onFail});
        };
    };

    /**
     * Given an item with {listId, text}, add it and  toggle
     * the add item action on success.
     * @param item
     * @returns {function(*, *)}
     */
    static asyncAddItem = (item) => {
       return  (dispatch, getState) => {
           dispatch(Actions.setItemFetching(true));

           // on item add success
           const onSuccess = item => {
               dispatch(Actions.addItem(item));
               dispatch(Actions.asyncSaveStateToStorage());
               dispatch(Actions.setItemFetching(false));
           };
           itemAddCall({
               item,
               token: getState().currentUser.user.authToken,  // add the token from the state
               onSuccess,
               onFail: () => dispatch(Actions.setItemFetching(false))});
       };
    };

    /**
     * Given an item with an id and listId, toggle its checked
     * state
     * @param item
     * @returns {function(*, *)}
     */
    static asyncToggleItem = (item) => {
        return  (dispatch, getState) => {
            dispatch(Actions.setItemFetching(true));

            /// get the item with the given id and listId
            let newItem = getState().items.entities.find(
                (it) => item.listId === it.listId && item.id === it.id);
            newItem.isChecked = !newItem.isChecked; // toggle the state

            // on item update success
            const onSuccess = item => {
                dispatch(Actions.updateItem(item));
                dispatch(Actions.asyncSaveStateToStorage());

                dispatch(Actions.setItemFetching(false)); // end api call flag
            };
            itemUpdateCall({
                item: newItem,  // the updated item
                token: getState().currentUser.user.authToken,  // add the token from the state
                onSuccess,
                onFail: () => dispatch(Actions.setItemFetching(false))});
        };
    };

    static asyncGetItems = (list) => {
        return (dispatch, getState) => {
            dispatch(Actions.setItemFetching(true));
            // on item get success, returns them all and sets them
            const onSuccess = items => {

                dispatch(Actions.setItems(items));
                dispatch(Actions.setItemFetching(false)); // end api call flag
                dispatch(Actions.asyncSaveStateToStorage());
            };
            itemGetCall({
                list,  // the list ot fetch from
                token: getState().currentUser.user.authToken,  // add the token from the state
                onSuccess,
                onFail: () => dispatch(Actions.setItemFetching(false))
            });
        };
    };

    static asyncSaveStateToStorage = () => {
        return (dispatch, getState) => {
            saveStateToStorage(getState());
        };
    };

    /**
     * Get the items given a list with {id}
     * @param list the list with {id}
     * @returns {function(*, *)}
     */
    static asyncPollForItems = (list) => {
        return  (dispatch, getState) => {
            // only do this if logged in
            if(getState().currentUser.user.name !== null) {
                dispatch(Actions.asyncGetItems(list))
            }
        };
    };
};