/**
 * Created by nikitautiu on 1/4/18.
 */

import React from "react";
import {Text, TouchableHighlight, StyleSheet, TouchableOpacity} from "react-native";

/* Presentational  component */
export const TodoItem = ({text, isChecked, onClick}) => (
    <TouchableOpacity onPress={() => onClick()}>
        <Text style={isChecked ? styles.checked : styles.unchecked}>
            {text}
        </Text>
    </TouchableOpacity>
);

const styles = StyleSheet.create({
    checked: {
        textDecorationLine: 'line-through',
        textDecorationStyle: 'solid'
    },
    unchecked: {}
});