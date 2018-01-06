/**
 * Created by nikitautiu on 1/4/18.
 */

import React from "react";
import AddTodo from "./AddTodo";
import {TodoList} from "./TodoList";
import {View,  StyleSheet, StatusBar, KeyboardAvoidingView, ActivityIndicator} from "react-native";
import {ReactInterval} from "react-interval";
/**
 *
 * @param isFetching
 * @param items the list of entities
 * @param onAdd callback for adding the item, receives a text
 * @param onToggle callback for toggling an item, receives an item with (id)
 * @param onUpdate
 */
const ListPage = ({isFetching, items, onAdd, onToggle, onUpdate}) => (
    <KeyboardAvoidingView behavior="padding" style={styles.container}>
        <ReactInterval callback={onUpdate} enabled={true} timeout={5000}/>
        <View style={styles.innerContainer}>
            <StatusBar barStyle="light-content"/>
            <View style={styles.spinnerContainer}>
                {isFetching && <ActivityIndicator size="small" color="#0000ff" />}
            </View>
            <AddTodo onSubmit={(text) => onAdd(text)} />
            <TodoList items={items} onClick={(id) => onToggle(id)} />
        </View>
    </KeyboardAvoidingView>
);

// define your styles
const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
    },
    innerContainer: {
        padding: 20
    },
    spinnerContainer: {
        height: 10
    },
    loginContainer:{
        alignItems: 'center',
        flexGrow: 1,
        justifyContent: 'center'
    }
});

export default ListPage;