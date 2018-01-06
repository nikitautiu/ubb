/**
 * This file defines the container
 * As opposed to LoginPge which only defines a
 * presentational component.
 */
import {connect} from "react-redux";
import LoginPage from "../components/LoginPage";
import Actions from "../state/Actions";

const mapStateToProps = (state) => state.currentUser;

const mapDispatchToProps = (dispatch) => ({
    logInUser: (username, password) => {
        dispatch(Actions.asyncLoginUser({username, password}));
    }
});

//make this component available to the app
export default Login = connect(mapStateToProps, mapDispatchToProps)(LoginPage);