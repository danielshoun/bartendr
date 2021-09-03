import {FormEvent, useState} from "react";
import {useHistory} from "react-router-dom";
import {useSession} from "../context";
import csrfFetch from "../utils/csrfFetch";

interface LoginForm {
    email: String;
    password: String;
    rememberMe: boolean;
}

export default function Login() {
    const history = useHistory();
    const {setSession} = useSession();
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [rememberMe, setRememberMe] = useState(false);

    async function handleSubmit(e: FormEvent) {
        e.preventDefault();
        const body: LoginForm = {
            email,
            password,
            rememberMe
        };
        const res = await csrfFetch("/api/v1/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(body)
        });
        if (res.ok) {
            const data = await res.json();
            setSession && setSession(data);
            history.push("/");
        }
    }

    return (
        <>
            <h1>Login</h1>
            <form onSubmit={handleSubmit}>
                <label htmlFor="email">Email</label>
                <input
                    type="text"
                    name="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <label htmlFor="password">Password</label>
                <input
                    type="password"
                    name="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <label htmlFor="rememberMe">Stay Logged In?</label>
                <input
                    type="checkbox"
                    name="rememberMe"
                    checked={rememberMe}
                    onChange={(e) => setRememberMe(e.target.checked)}
                />
                <button type="submit">Login</button>
            </form>
        </>
    );
}