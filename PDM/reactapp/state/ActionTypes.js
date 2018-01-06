export default defineActionConstants([
    'LOG_IN_SUCCESS',
    'LOG_IN_FAIL',
    'LOG_IN_START',

    'UPDATE_ITEM',
    'ADD_ITEM',
    'ITEM_FETCH_START',
    'ITEM_FETCH_STOP',
    'SET_ITEMS'
]);

/**
 * Defines an object encapsulating the string constants
 * as fields.
 */
function defineActionConstants(names) {
    return names.reduce((result, name) => {
        result[name] = name;
        return result;
    }, {});
}