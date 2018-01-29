/**
 * Created by nikitautiu on 1/4/18.
 */
import React, {Component} from "react";
import {connect} from "react-redux";
import Actions from "../state/Actions";
import {Button, Container, Content, Fab, Header, Icon, Left, Right, Title, Toast, View} from "native-base";
import {ReactInterval} from "react-interval";
import {ItemList} from "../components/ItemList";

/**
 * The container for the list page
 */
class List extends Component {
    static navigationOptions = ({navigation}) => ({header: null}); // override header rendering
    /*
    Callback for list updates, calls a toast for update
     */
    onUpdate = () => {
        // callback for updating list, also shows a toast
        this.props.onUpdate();
    };

    componentDidMount() {
        const intervalId = setInterval(this.onUpdate, 5000);
        // store intervalId in the state so it can be accessed later:
        this.setState({intervalId: intervalId});
    }

    componentWillUnmount() {
        // use intervalId from the state to clear the interval
        clearInterval(this.state.intervalId);
    }

    openEditDialog = (id) => {
        // get the item which was clicked and pass it to the edit screen
        this.props.navigation.navigate('Edit', {item: this.props.items.find((it) => id === it.id)});
    };

    openAddDialog = () => {
        // just open the other screen
        this.props.navigation.navigate('Add');
    };

    render() {
        const {isFetching, isLoggedIn, items, onToggle} = this.props; // isFetching not currently used, but could be for a spinner
        return (
            <Container>
                <NavigationHeader
                    headerTitle="List"
                    leftHeaderIcon="person"
                    onLeftHeaderPress={() => this.props.navigation.goBack()}
                    rightHeaderIcon="podium"
                    onRightHeaderPress={() => this.props.navigation.navigate('Chart')}
                />
                <Content>
                    <ItemList items={items} onClick={(id) => onToggle(id)}
                              onLongClick={(id) => this.openEditDialog(id)}
                    />
                </Content>
                <Fab
                    position="bottomRight"
                    onPress={() => this.openAddDialog()}
                    style={{backgroundColor: '#5067FF'}}
                >
                    <Icon name="add"/>
                </Fab>
            </Container>
        );
    }
}


const mapStateToProps = (state) => ({
    items: state.items.entities.filter(item => item.listId === 1),
    isFetching: state.items.isFetching,
    isLoggedIn: !!state.currentUser.user.authToken
});

/// list 1 hardcoded
const mapDispatchToProps = (dispatch) => ({
    onToggle: (id) => dispatch(Actions.asyncToggleItem({listId: 1, id: id})),
    onUpdate: () => dispatch(Actions.asyncPollForItems({id: 1}))
});


// connect and export
export default connect(mapStateToProps, mapDispatchToProps)(List);
