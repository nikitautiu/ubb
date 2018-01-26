/**
 * This file defines the container
 * As opposed to LoginPge which only defines a
 * presentational component.
 */
import React, {Component} from "react";
import {connect} from "react-redux";
import LoginPage from "../components/LoginPage";
import Actions from "../state/Actions";
import {Body, Button, Header, Icon, Left, Right, Title} from "native-base";

const mapStateToProps = (state) => state.currentUser;

const mapDispatchToProps = (dispatch) => ({
    logInUser: (username, password) => {
        dispatch(Actions.asyncLoginUser({username, password}));
    },
    logOutUser: () => {
        dispatch(Actions.logout());
    }
});

//make this component available to the app
const LoginContainer = connect(mapStateToProps, mapDispatchToProps)(LoginPage);
LoginContainer.navigationOptions = ({navigation}) => ({
    header: (
        <Header>
            <Left/>
            <Body>
            <Title>Login</Title>
            </Body>
            <Right>
                <Button transparent onPress={() => navigation.navigate("List")}>
                    <Icon name="arrow-forward" style={{color: "white"}}/>
                </Button>
            </Right>
        </Header>
    )
});

export default LoginContainer;