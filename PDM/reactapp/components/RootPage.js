/**
 * Created by nikitautiu on 1/3/18.
 */
import React from 'react';
import List from "../containers/List";
import Login from "../containers/Login";

// create a component
const RootPage = ({isLogged}) => (
    isLogged ? <List /> : <Login />
);

export default RootPage;