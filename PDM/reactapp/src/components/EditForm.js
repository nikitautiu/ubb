import React, {Component} from "react";
import {Button, Form, Input, Item, Label, Text} from "native-base";

export default class EditForm extends Component {
    constructor(props) {
        super(props);
        if(!!props.values)
            this.state = {values: props.values};
        else
            this.state = {values: {text: ""}};
    }

    onChange = (field, value) => {
        return this.setState({
            values: {
                ...this.state.values,
                [field]: value
            }
        });
    };

    render() {
        return (
            <Form>
                <Item inlineLabel error={!!this.props.isError}>
                    <Label>Text</Label>
                    <Input
                        ref={(input) => this.textInput = input}
                        value={this.state.values.text}
                        autoCorrect={false}
                        keyboardType='default'
                        onChangeText={text => this.onChange('text', text)}
                    />
                </Item>
                <Button primary block onPress={() => this.onSubmit()}><Text> DONE </Text></Button>

            </Form>
        );
    }

    /**
     * Sends back the values of the form
     */
    onSubmit = () => {
        this.props.onSubmit(this.state.values)
    }
}