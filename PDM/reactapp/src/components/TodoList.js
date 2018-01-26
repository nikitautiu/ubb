/**
 * Created by nikitautiu on 1/4/18.
 */
/**
 * Created by nikitautiu on 1/4/18.
 */

import React from "react";
import {FlatList} from "react-native";
import {TodoItem} from "./TodoItem";

/* Presentational  component */
export const TodoList = ({items, onClick}) => (
    <FlatList
        data={items}
        renderItem={({item}) => <TodoItem isChecked={item.isChecked}
                                          text={item.text}
                                          onClick={() => onClick(item.id)} />}
        keyExtractor={(item, index) => index}
    />
);

