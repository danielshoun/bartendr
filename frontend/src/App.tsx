import React from "react";
import {Route, Switch} from "react-router-dom";
import Dashboard from "./components/Dashboard";
import Home from "./components/Home";
import Login from "./components/Login";
import Nav from "./components/Nav";
import ProtectedRoute from "./components/ProtectedRoute";
import Register from "./components/Register";

function App() {
    return (
        <>
            <Nav/>
            <Switch>
                <Route exact path="/">
                    <Home/>
                </Route>
                <Route exact path="/login">
                    <Login/>
                </Route>
                <Route exact path="/register">
                    <Register/>
                </Route>
                <ProtectedRoute exact path="/dashboard">
                    <Dashboard/>
                </ProtectedRoute>
            </Switch>
        </>
    );
}

export default App;
