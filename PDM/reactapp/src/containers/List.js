/**
 * Created by nikitautiu on 1/4/18.
 */
import React from "react";
import {connect} from "react-redux";
import Actions from "../state/Actions";
import ListPage from "../components/ListPage";
import {Body, Header, Icon, Left, Right, Title, Button} from "native-base";


const mapStateToProps = (state) => ({
    items: state.items.entities.filter(item => item.listId === 1),
    isFetching: state.items.isFetching
});

/// list 1 hardcoded
const mapDispatchToProps = (dispatch) => ({
    onAdd: (text) => dispatch(Actions.asyncAddItem({listId: 1, text})),
    onToggle: (id) => dispatch(Actions.asyncToggleItem({listId: 1, id: id})),
    onUpdate: () => dispatch(Actions.asyncPollForItems({id: 1}))
});

//make this component available to the app
const List = connect(mapStateToProps, mapDispatchToProps)(ListPage);

// add custom header
List.navigationOptions = ({navigation}) => ({
    header: (
        <Header>
            <Left>
                <Button transparent onPress={() => navigation.navigate("Login")}>
                    <Icon name="person" style={{color: "white"}}/>
                </Button>
            </Left>
            <Body>
            <Title>List</Title>
            </Body>
            <Right/>
        </Header>
    )
});


export default List;