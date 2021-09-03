import {Redirect, Route, RouteProps} from "react-router-dom";
import {useSession} from "../context";

export default function ProtectedRoute(props: RouteProps) {
    const {session} = useSession();
    return (
        <Route {...props}>
            {session ? props.children : <Redirect to="/login"/>}
        </Route>
    );
}