/**
 * Created by nikitautiu on 1/4/18.
 */
import React, { Component } from 'react';
import {Text, TextInput, TouchableOpacity, View, StyleSheet} from "react-native";


class AddTodo extends Component {
    constructor(props) {
        super(props);
        this.state = {values: {text: ''}};
    }

    onChange = (field, value) => this.setState({
        values: {
            ...this.state.values,
            [field]: value
    }
    });

    onSubmit = () => this.props.onSubmit(this.state.values.text);

    render = () => (
        <View style={styles.container}>
            <TextInput style={styles.input}
                       autoCapitalize="none"
                       onSubmitEditing={() => this.onSubmit()}
                       autoCorrect={false}
                       keyboardType='default'
                       placeholder='New Item'
                       onChangeText={text => this.onChange('text', text)}
            />

            <TouchableOpacity style={styles.button} onPress={() => this.onSubmit()}>
                <Text style={styles.buttonText}>ADD</Text>
            </TouchableOpacity>
        </View>
    );
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

    buttonText: {
        color: '#fff',
        textAlign: 'center',
        fontWeight: '700'
    },
    button:{
        backgroundColor:  '#2980b6',
        marginHorizontal: 50,
        paddingVertical: 10,
    }

});

//make this component available to the app
export default AddTodo;