/**
 * Created by nikitautiu on 1/3/18.
 */
import React from 'react';
import { View, StyleSheet,KeyboardAvoidingView } from 'react-native';
import LoginForm from './LoginForm';
import Spinner from "./Spinner";

// create a component
const LoginPage = ({isFailed, isFetching, logInUser}) => (
    isFetching ? <Spinner/> :
        <KeyboardAvoidingView behavior="padding" style={styles.container}>
            <View style={styles.container}>
                <LoginForm isFailed={isFailed} logInUser={logInUser}/>
            </View>
        </KeyboardAvoidingView>
);

// define your styles
const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
    }
});

export default LoginPage;