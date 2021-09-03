import Cookies from "js-cookie";

export default async function csrfFetch(url: RequestInfo, options: RequestInit) {
    return await fetch(url, {
        ...options,
        headers: {...options.headers, "X-XSRF-TOKEN": Cookies.get("XSRF-TOKEN")} as HeadersInit
    });
}