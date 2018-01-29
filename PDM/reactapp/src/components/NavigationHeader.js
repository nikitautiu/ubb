import React, {Component} from 'react';
import {Body, Button, Header, Icon, Left, Right, Title} from "native-base";

/**
 * Component to extend from if making a page with a header
 * can be passed a icon names, a title, and callbacks for the buttons
 */
export default NavigationHeader = ({headerTitle, leftHeaderIcon=null, rightHeaderIcon=null,
                                  onLeftHeaderPress=()=>{}, onRightHeaderPress=()=>{}}) => (
    <Header>
        <Left>
            {leftHeaderIcon &&
            <Button transparent onPress={() => onLeftHeaderPress()}>
                <Icon name={leftHeaderIcon} style={{color: "white"}}/>
            </Button>
            }
        </Left>
        <Body>
        {headerTitle &&
        <Title>{headerTitle}</Title>
        }
        </Body>
        <Right>
            {rightHeaderIcon &&
            <Button transparent onPress={() => onRightHeaderPress()}>
                <Icon name={rightHeaderIcon} style={{color: "white"}}/>
            </Button>
            }
        </Right>
    </Header>
);