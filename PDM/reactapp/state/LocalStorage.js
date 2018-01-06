import { AsyncStorage } from 'react-native';

/**
 * Async calls to local storage. They can be use blockingly
 * or not.
 */

const Keys = {
    items: 'items',
};

async function getItemsAsync() {
    let results = await AsyncStorage.getItem(Keys.items);

    try {
        return JSON.parse(results);
    } catch(e) {
        return null;
    }
}

const saveItemsAsync = (items) => {
    return AsyncStorage.setItem(Keys.items, JSON.stringify(items));
};

const removeItemsAsync = () => {
    return AsyncStorage.removeItem(Keys.items);
}


export function clearAllAsync() {
    return AsyncStorage.clear();
}

/**
 * Save the state to the local storage
 * Mainly just the items/
 * @param state
 */
export const saveStateToStorage = (state) => {
    console.log("Saved items locally");
    const items = state.items;
    return saveItemsAsync(items); // save them to async
};

export const getStateFromStorage = () => ({
    items: {
        entities: getItemsAsync()
    }
});

