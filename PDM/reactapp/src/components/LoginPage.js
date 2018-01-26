/**
 * Created by nikitautiu on 1/3/18.
 */
import React from 'react';
import {Dimensions, KeyboardAvoidingView, Linking} from 'react-native';
import LoginForm from './LoginForm';
import {Button, Container, Content, Header, Spinner, Text, Title, View} from "native-base";

// create a component
const LoginPage = ({isFailed, isFetching, logInUser, logOutUser, user}) => {
    return (
            <Container>
                <Content padder contentContainerStyle={{flex: 1, justifyContent: 'center'}}>
                    <View style={{marginBottom: 10}}>
                    {!user.authToken &&
                        <LoginForm isFailed={isFailed} logInUser={logInUser}/>
                    }
                    {user.authToken &&
                        <Button  block danger onPress={() => logOutUser()}>
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
};


export default LoginPage;