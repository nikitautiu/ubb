/**
 * This file defines the container
 * As opposed to LoginPge which only defines a
 * presentational component.
 */
import React, {Component} from "react";
import {connect} from "react-redux";
import Actions from "../state/Actions";
import {Button, Container, Content, Header, Icon, Left, Right, Root, Spinner, Title, View} from "native-base";
import NavigationHeader from "../components/NavigationHeader";
import EditForm from "../components/EditForm";


// create a component
class AddPage extends Component {
    static navigationOptions = ({navigation}) => ({header: null}); // override header rendering

    render() {
        return (
            <Container>
                <NavigationHeader
                    headerTitle="Add Todo"
                    leftHeaderIcon="arrow-back"
                    onLeftHeaderPress={() => this.props.navigation.goBack()}
                />
                <Content padder contentContainerStyle={{flex: 1, justifyContent: 'center'}}>
                    <EditForm onSubmit={(values) => this.props.onAdd(values)}/>
                </Content>
            </Container>
        );
    }
};


// no state here
const mapStateToProps = (state) => ({
});


// returning the values means adding it to the list
const mapDispatchToProps = (dispatch) => ({
    onAdd: ({text}) => dispatch(Actions.asyncAddItem({listId: 1, text})),
});


// export the connected component
export default  connect(mapStateToProps, mapDispatchToProps)(AddPage);