import { writable } from 'svelte/store';
import { browser } from '$app/environment';

const storedToken = browser ? localStorage.getItem('jwt_token') : null;
const storedUser = browser ? localStorage.getItem('user_data') : null;

export const token = writable(storedToken);

export const user = writable(storedUser ? JSON.parse(storedUser) : null);

if (browser) {
    token.subscribe((val) => {
        if (val) localStorage.setItem('jwt_token', val);
        else localStorage.removeItem('jwt_token');
    });

    user.subscribe((val) => {
        if (val) localStorage.setItem('user_data', JSON.stringify(val));
        else localStorage.removeItem('user_data');
    });
}

export function logout() {
    token.set(null);
    user.set(null);
    if (browser) window.location.href = '/';
}