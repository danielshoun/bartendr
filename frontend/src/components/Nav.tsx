import {Link, useHistory} from "react-router-dom";
import {useSession} from "../context";
import Cookies from "js-cookie";

export default function Nav() {
    const history = useHistory();
    const {session, setSession} = useSession();

    function handleLogout() {
        Cookies.remove("TOKEN");
        setSession && setSession(null);
    }

    return (
        <div className="nav">
            <Link className="nav-link" to="/">Home</Link>
            {session ?
                <>
                    <Link className="nav-link" to="/dashboard">Dashboard</Link>
                    <Link className="nav-link" to="/" onClick={handleLogout}>Logout</Link>
                </> :
                <>
                    <Link className="nav-link" to="/login">Login</Link>
                    <Link className="nav-link" to="/register">Register</Link>
                </>
            }
        </div>
    );
}