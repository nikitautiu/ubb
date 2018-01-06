/**
 * Created by nikitautiu on 1/4/18.
 */

import {connect} from "react-redux";
import Actions from "../state/Actions";
import ListPage from "../components/ListPage";


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
export default List = connect(mapStateToProps, mapDispatchToProps)(ListPage);