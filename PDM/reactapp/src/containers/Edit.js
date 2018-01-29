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
class EditPage extends Component {
    static navigationOptions = ({navigation}) => ({header: null}); // override header rendering

    render() {
        // pass the text to the form
        const {text} = this.props.navigation.state.params.item;
        return (
            <Container>
                <NavigationHeader
                    headerTitle="Edit Todo"
                    leftHeaderIcon="arrow-back"
                    onLeftHeaderPress={() => this.props.navigation.goBack()}
                />
                <Content padder contentContainerStyle={{flex: 1, justifyContent: 'center'}}>
                    <EditForm values={{text}} onSubmit={(values) => this.onSubmit(values)} />
                </Content>
            </Container>
        );
    }

    onSubmit = (values) => {
        // what happens when you submit the item

        const newItem = {...this.props.navigation.state.params.item, ...values};
        this.props.onEdit(newItem);
    };
};


// no state here
const mapStateToProps = (state) => ({
});


// update the item on button press
const mapDispatchToProps = (dispatch) => ({
    onEdit: (item) => dispatch(Actions.asyncUpdateItem({...item, listId: 1})),
});


// export the connected component
export default connect(mapStateToProps, mapDispatchToProps)(EditPage);