import {DrawerNavigator, StackNavigator} from "react-navigation";
import React from "react";
import Login from "./containers/Login";
import List from "./containers/List";
import {Provider} from "react-redux";
import Store from "./state/Store";


const RootNavigator = StackNavigator(
    {
        Login: {
            screen: Login,
            navigationOptions: {
                headerTitle: 'Login',
            },
        },
        List: {
            screen: List,
            navigationOptions: {
                headerTitle: 'List',
            },
        }
    }

);

export class App extends React.Component {
    render() {
        return (
            <Provider store={Store}>
                <RootNavigator/>
            </Provider>
        );
    }
};