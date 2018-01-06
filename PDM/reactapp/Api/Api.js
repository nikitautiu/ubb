/**
 * Created by nikitautiu on 1/3/18.
 */
import {fetch} from "cross-fetch";

export const apiUrl = 'http://192.168.0.207:8000';

/**
 * Makes a call to the api given an endpoint, a payload
 * to send as json. Optionally receives a token to use for authentication
 * and a callback to use for token refresh.
 * Receives 2 callbacks, one for success and one for failure.
 * Can be passed
 * @param endpoint the endpoint to call
 * @param payload the
 * @param onReceive
 * @param token
 * @param additionalHeaders additional headers and their values
 * @param httpMethod
 * @returns {*|Promise.<T>}
 */
const apiCall = ({endpoint,
                     payload = null,
                     onReceive = response => {},
                     token = null,
                     additionalHeaders = {},
                     httpMethod
                 }) => {
    let responseObj;  // for catching the response object before parsing it

    return fetch(
        `${apiUrl}${endpoint}`,
        {
            method: httpMethod,  // passable method
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                ...additionalHeaders,
                ...(token === null ? {} : {
                    'Authorization': `Bearer ${token}`
                })  // optional token bearer header
            },
            ...(payload === null ? {} : {
                body: JSON.stringify(payload)
            })
        }
    ).then(response => {
            responseObj = response;  // save the response status for return
            return response.json();
        }
    ).then(data => onReceive({status: responseObj.status, data, headers: responseObj.headers})
    ).catch(err => onReceive({status: -1}));  // means non-status error
};


/**
 * Tries to login. If successful, calls onSuccess with the token
 * otherwise onFail.
 * @param username
 * @param password
 * @param onSuccess
 * @param onFail
 * @returns {*|Promise.<T>}
 */
export const loginCall = ({username, password, onSuccess, onFail}) => {
    const payload = {username, password};
    const endpoint = `/tokens/auth/`;
    const httpMethod = 'POST';

    return apiCall({
        endpoint,
        httpMethod,
        payload,
        onReceive: response => response.status === 200 ? onSuccess(response.data.token) : onFail(),
        onFail
    });
};

/**
 * Updates the given item. Calls the onSuccess callback with the
 * returned item on success
 * @param item the new, updated item
 * @param token the jwt token to use
 * @param onSuccess the success callback
 * @param onFail the failure callback
 */
export const itemAddCall = ({item, onSuccess, onFail, token}) => {
    const payload = {text: item.text};
    const endpoint = `/lists/${item.listId}/items/`;
    const httpMethod = 'POST';

    return apiCall({
        endpoint,
        httpMethod,
        payload,
        token,
        // the token is passed from the component/thunk
        // where we can access state
        // the new item is received, but the fields must be renamed
        onReceive: response => (response.status === 201 ?
            onSuccess({
                ...storeItemFromApi(response.data),
                listId: item.listId
            }) :
            onFail()),
        onFail
    });
};


/**
 * Converts an api-formatted item to a local format one
 * Basically does the necessary renaming of fields for compatibility
 * @param data
 */
const storeItemFromApi = (data) => ({
    text: data.text,
    isChecked: data.checked,
    id: data.index,
});


/**
 * Updates the given item. Calls the onSuccess callback with the
 * returned item on success
 * @param item the new, updated item
 * @param token the jwt token to use
 * @param onSuccess the success callback
 * @param onFail the failure callback
 */
export const itemUpdateCall = ({item, onSuccess, onFail, token}) => {
    // convert back to api format
    const payload = {
        checked: item.isChecked,
        text: item.text
    };
    const endpoint = `/lists/${item.listId}/items/${item.id}/`;
    const httpMethod = 'PUT';

    return apiCall({
        endpoint,
        httpMethod,
        payload,
        token,  // send the token
        // the new item is received, but the fields must be renamed
        onReceive: response => response.status === 200 ?
            onSuccess({
                ...storeItemFromApi(response.data),
                listId: item.listId
            }) :
            onFail(),
        onFail
    });
};

/**
 * Returns the list of items for a given list
 * returned item on success if modified
 * @param listId the id of the list
 * @param token the jwt token to use
 * @param onSuccess the success callback
 * @param onFail the failure callback
 * @param lastModified the last modified token
 */
export const itemGetCall = ({list: {id: listId}, onSuccess, onFail, token, lastModified = ""}) => {
    const endpoint = `/lists/${listId}/items/`;
    const httpMethod = 'GET';

    return apiCall({
        endpoint,
        httpMethod,
        token,
        additionalHeaders: {
            "If-Modified-Since": lastModified  // last modified
        },
        // the token is passed from the component/thunk
        // where we can access state
        // the new item is received, but the fields must be renamed
        onReceive: response => (response.status === 200 ?
            // in case of success, returns a list with the converted items
            onSuccess(response.status === 304 ? null :  // return null if nothing changed
                {
                    items: response.data.map(
                        item => ({
                            ...storeItemFromApi(item),
                            listId // insert the list id alongside other data
                        })
                    ),
                    lastModified: response.headers.lastModified
                }
                ) :
            onFail()),
        onFail // doubles as a failure callback
    });
};