import React, {Component} from 'react';
import {Body, Icon, Left, List, ListItem, Right, Text} from "native-base";


/**
 * Dynamic list
 */
export const ItemList = ({items, onClick = (id) => {}, onLongClick = (id) => {}}) => (
    <List dataArray={items}
          renderRow={(item) =>
              <ListItem icon
                  onPress={() => onClick(item.id)}
                  onLongPress={() => onLongClick(item.id)}
              >
                  <Left>
                      {item.isChecked && <Icon name="checkmark"/>}
                  </Left>
                  <Body><Text>{item.text}</Text></Body>
                  <Right />
              </ListItem>
          }>
    </List>
);