/**
 * This file defines the container
 * As opposed to LoginPge which only defines a
 * presentational component.
 */
import React, {Component} from "react";
import {connect} from "react-redux";
import {Button, Container, Content, Header, Icon, Left, Right, Root, Spinner, Title, View} from "native-base";
import {Dimensions, KeyboardAvoidingView, Linking} from 'react-native';
import NavigationHeader from "../components/NavigationHeader";
import Pie from "react-native-pie";


// create a component
class ChartPage extends Component {
    static navigationOptions = ({navigation}) => ({header: null}); // override header rendering

    render() {
        const checked = this.props.items.filter((it) => it.isChecked).length;
        const unchecked = this.props.items.length - checked;
        debugger
        return (
            <Container>
                <NavigationHeader
                    headerTitle="Chart"
                    leftHeaderIcon="arrow-back"
                    onLeftHeaderPress={() => this.props.navigation.goBack()}
                />
                <Content contentContainerStyle={{flex: 1, justifyContent: 'center'}}>
                    <View style={{
                        flex: 1,
                        alignItems: 'center'
                    }}>
                        <Pie
                            radius={100}
                            series={[1. * checked / (checked + unchecked) * 100, 1. * unchecked / (checked + unchecked) * 100]}
                            colors={['#E38627', '#C13C37']}
                        />
                    </View>
                </Content>
            </Container>
        );
    }
};


const mapStateToProps = (state) => ({
    items: state.items.entities.filter(item => item.listId === 1)
});

const mapDispatchToProps = (dispatch) => ({});

// connect and export
export default connect(mapStateToProps, mapDispatchToProps)(ChartPage);