import {createContext, Dispatch, SetStateAction, useContext, useEffect, useState} from "react";

interface SessionData {
    id: BigInt;
    firstName: String;
    lastName: String;
    email: String;
}

interface SessionContextType {
    session: SessionData | null;
    setSession: Dispatch<SetStateAction<SessionData | null>> | null;

}

export const SessionContext = createContext<SessionContextType>({session: null, setSession: null});

export function useSession() {
    return useContext(SessionContext);
}

interface Props {
    children: JSX.Element;
}

export default function SessionProvider({children}: Props) {
    const [session, setSession] = useState<SessionData | null>(null);
    const [loaded, setLoaded] = useState(false);

    useEffect(() => {
        async function auth() {
            const res = await fetch("/api/v1/auth/self");
            if (res.ok) {
                const data = await res.json();
                setSession(data);
            }
        }
        auth().then(() => setLoaded(true))
    }, []);

    if (!loaded) {
        return (
            <></>
        );
    }

    return (
        <SessionContext.Provider
            value={{session, setSession}}
        >
            {children}
        </SessionContext.Provider>
    );
}