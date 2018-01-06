/**
 * Define the store basically made of immutable objects
 * Immutable assures us we are not directly modifying state
 * isFetching defines whether there is an ongoing call to the
 * API.
 */
export const defaultCurrentUser = {
    isFetching: false,
    isFailed: false,
    user: {
        authToken: null,
        name: null,
    }
};

export const defaultLists = {
    isFetching: false,
    entities: []
};

export const defaultItems = {
    isFetching: false,
    lastFetched: null,
    entities: [

    ]
};