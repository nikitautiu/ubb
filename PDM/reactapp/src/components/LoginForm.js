import React, {Component} from 'react';
import {Button, Container, Content, Form, Input, Item, Label, Text, Toast, View} from "native-base";

// create a component
class LoginForm extends Component {
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
                <Item inlineLabel last>
                    <Label>Password</Label>
                    <Input
                        ref={(input) => this.passwordInput = input}
                        secureTextEntry
                        returnKeyType="go"
                        onChangeText={text => this.onChange('password', text)}
                        onSubmitEditing={() => this.onSubmit()}
                    />
                </Item>
                <Button primary block onPress={() => this.onSubmit()}><Text> LOGIN </Text></Button>

            </Form>
        );
    };

    constructor(props) {
        super(props);
        this.state = {credentials: {username: '', password: '', showedError: false}};
    }

    componentDidUpdate(prevProps, prevState, prevContext) {
        console.log(this.props);
        if (this.props.isFailed && !this.state.showedError) {
            Toast.show({
                text: 'Invalid username or password',
                position: 'bottom',
            });
            this.setState(this.setState({showedError: true}));
        }
    }
}


//make this component available to the app
export default LoginForm;