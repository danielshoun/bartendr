import {useSession} from "../context";

export default function Home() {
    const {session} = useSession();

    return (
        <>
            <h1>Home</h1>
            {session ?
                <p>You are authenticated as {session.email}.</p> :
                <p>You are not authenticated.</p>
            }
        </>
    );
}