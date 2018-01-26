import React, {Component} from 'react';
import {Button, Container, Content, Form, Input, Item, Label, Text, Toast, View} from "native-base";

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
        }
    });

    onSubmit = () => this.props.logInUser(this.state.credentials.username, this.state.credentials.password);

    render = () => {

        return (

            <Form>
                <Item inlineLabel>
                    <Label>Username</Label>
                    <Input
                        ref={(input) => this.usernameInput = input}
                        onSubmitEditing={() => {
                            this.passwordInput._root.focus();
                        }}
                        autoCorrect={false}
                        keyboardType='default'
                        returnKeyType='next'
                        onChangeText={text => this.onChange('username', text)}
                    />
                </Item>
                <Item inlineLabel last error={this.props.isFailed}>
                    <Label>Password</Label>
                    <Input
                        ref={(input) => this.passwordInput = input}
                        secureTextEntry
                        returnKeyType="go"
                        onChangeText={text => this.onChange('password', text)}
                        onSubmitEditing={() => this.onSubmit()}
                    />
                </Item>
                {this.props.isFailed && <Text>Invalid username or password!</Text> }
                <Button primary block onPress={() => this.onSubmit()}><Text> LOGIN </Text></Button>

            </Form>


        );
    }
}


//make this component available to the app
export default LoginForm;