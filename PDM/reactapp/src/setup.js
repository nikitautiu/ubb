import {DrawerNavigator, StackNavigator} from "react-navigation";
import React from "react";
import Login from "./containers/Login";
import List from "./containers/List";
import {Provider} from "react-redux";
import Store from "./state/Store";
import {Root} from "native-base";
import Add from "./containers/Add";
import Edit from "./containers/Edit";
import Chart from "./containers/Chart";


const RootNavigator = StackNavigator(
    {
        Login: {
            screen: Login,
        },
        List: {
            screen: List,

        },
        Add: {
            screen: Add,
        },
        Edit: {
            screen: Edit
        },
        Chart: {
            screen: Chart
        }
    }
);

export class App extends React.Component {
    render() {
        return (
            <Provider store={Store}>
                <Root>
                    <RootNavigator/>
                </Root>
            </Provider>
        );
    }
};