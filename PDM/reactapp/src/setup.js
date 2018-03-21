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
import {Easing, Animated} from "react-native";


const transitionConfig = () => {
    return {
        transitionSpec: {
            duration: 750,
            easing: Easing.out(Easing.poly(4)),
            timing: Animated.timing,
            useNativeDriver: true,
        },
        screenInterpolator: sceneProps => {
            const {layout, position, scene} = sceneProps

            const thisSceneIndex = scene.index
            const width = layout.initWidth

            const translateX = position.interpolate({
                inputRange: [thisSceneIndex - 1, thisSceneIndex],
                outputRange: [width, 0],
            });

            return {transform: [{translateX}]}
        },
    }
};

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
    },
    {
        initialRouteName: 'Login',
        transitionConfig,
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