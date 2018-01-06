import {connect} from "react-redux";
import RootPage from "../components/RootPage";

const mapStateToProps = (state) => ({isLogged: state.currentUser.user.name !== null});

//make this component available to the app
export default Root = connect(mapStateToProps)(RootPage);