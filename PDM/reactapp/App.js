import React from 'react'
import {Provider} from "react-redux";
import Store from "./state/Store";
import Root from "./containers/Root";


const AppContainer = () => {
    return (
        <Provider store={Store}>
            <Root />
        </Provider>
    );
};

export default AppContainer;
