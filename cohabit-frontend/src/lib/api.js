import { get } from 'svelte/store';
import { token, logout } from '$lib/auth';

const SERVICES = {
    auth: 'http://localhost:8080',
    complex: 'http://localhost:8081',
    rating: 'http://localhost:8082'
};

export async function apiCall(service, endpoint, method = 'GET', body = null) {
    const jwt = get(token);

    const headers = {
        'Content-Type': 'application/json',
        ...(jwt && { 'Authorization': `Bearer ${jwt}` })
    };

    const config = {
        method,
        headers,
        ...(body && { body: JSON.stringify(body) })
    };

    try {
        const response = await fetch(`${SERVICES[service]}${endpoint}`, config);

        if (response.status === 401) {
            console.warn("Session abgelaufen. Logout...");
            logout();
            throw new Error("Session expired");
        }

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Error ${response.status}: ${errorText}`);
        }

        const text = await response.text();
        return text ? JSON.parse(text) : {};

    } catch (err) {
        console.error('API Call Failed:', err);
        throw err;
    }
}