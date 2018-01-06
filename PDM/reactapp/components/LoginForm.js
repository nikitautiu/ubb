//import liraries
import React, { Component } from 'react';
import {View, Text, TextInput, TouchableOpacity, StyleSheet ,StatusBar} from 'react-native';


// create a component
class LoginForm extends Component {
    constructor(props) {
        super(props);
        this.state = {credentials: {username: '', password: ''}};
    }

    onChange = (field, value) => this.setState({
        credentials: {
        ...this.state.credentials,
        [field]: value
    }});

    onSubmit = () => this.props.logInUser(this.state.credentials.username, this.state.credentials.password);

    render = () => (
        <View style={styles.container}>
            <StatusBar barStyle="light-content"/>
            <TextInput style={styles.input}
                       autoCapitalize="none"
                       onSubmitEditing={() => this.passwordInput.focus()}
                       autoCorrect={false}
                       keyboardType='default'
                       returnKeyType="next"
                       placeholder='Username'
                       onChangeText={text => this.onChange('username', text)}
            />

            <TextInput style={styles.input}
                       returnKeyType="go" ref={(input) => this.passwordInput = input}
                       placeholder='Password'
                       onChangeText={text => this.onChange('password', text)}
                       secureTextEntry
            />
            {this.props.isFailed && <Text style={styles.errorMsg}> Wrong user or password </Text>}
            <TouchableOpacity style={styles.buttonContainer} onPress={() => this.onSubmit()}>
                <Text style={styles.buttonText}>LOGIN</Text>
            </TouchableOpacity>
        </View>
    )
}

// define your styles
const styles = StyleSheet.create({
    container: {
        padding: 20
    },
    input:{
        height: 40,
        marginBottom: 10,
        padding: 10,
    },
    buttonContainer:{
        backgroundColor: '#2980b6',
        paddingVertical: 15
    },

    errorMsg:{
        color: '#ff2222',
        paddingVertical: 15
    },
    buttonText:{
        color: '#fff',
        textAlign: 'center',
        fontWeight: '700'
    },
    loginButton:{
        backgroundColor:  '#2980b6',
        color: '#fff'
    }

});

//make this component available to the app
export default LoginForm;