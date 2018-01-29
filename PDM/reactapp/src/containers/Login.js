/**
 * This file defines the container
 * As opposed to LoginPge which only defines a
 * presentational component.
 */
import React, {Component} from "react";
import {connect} from "react-redux";
import Actions from "../state/Actions";
import {Button, Container, Content, Header, Icon, Left, Right, Root, Spinner, Text, Title, View} from "native-base";
import {Dimensions, KeyboardAvoidingView, Linking} from 'react-native';
import LoginForm from "../components/LoginForm";
import NavigationHeader from "../components/NavigationHeader";


// create a component
class LoginPage extends Component {
    static navigationOptions = ({navigation}) => ({header: null}); // override header rendering

    render() {
        const {isFailed, isFetching, logInUser, logOutUser, user} = this.props;
        return (
            <Container>
                <NavigationHeader
                    headerTitle="Login"
                    rightHeaderIcon="list"
                    onRightHeaderPress={() => this.props.navigation.navigate('List')}
                />
                <Content padder contentContainerStyle={{flex: 1, justifyContent: 'center'}}>
                    <View style={{marginBottom: 10}}>
                        {!user.authToken &&
                        <LoginForm isFailed={isFailed} logInUser={logInUser}/>
                        }
                        {user.authToken &&
                        <Button block danger onPress={() => logOutUser()}
                        >
                            <Text> LOGOUT </Text>
                        </Button>
                        }
                    </View>
                    <Button primary block onPress={() => Linking.openURL('mailto:nikita.utiu@gmail.com')}>
                        <Text> Problems? </Text>
                    </Button>
                    {isFetching && <Spinner/>}
                </Content>
            </Container>
        );
    }
};


const mapStateToProps = (state) => state.currentUser;

const mapDispatchToProps = (dispatch) => ({
    logInUser: (username, password) => {
        dispatch(Actions.asyncLoginUser({username, password}));
    },
    logOutUser: () => {
        dispatch(Actions.logout());
    }
});

// connect and export
export default connect(mapStateToProps, mapDispatchToProps)(LoginPage);