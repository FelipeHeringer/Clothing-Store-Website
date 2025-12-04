import { api } from "./api";

type RegisterData = {
    username: string,
    email: string,
    password: string
}

type LoginCredentials = {
    email: string;
    password: string;
};

interface User {
    userId: string;
    username: string;
    email: string;
}

interface AuthResponse {
    success: boolean;
    accessToken?: string;
    refreshToken?: string;
    safeUser?: User;
    message?: string;
}

export const authService = {

    async register(userData: RegisterData): Promise<AuthResponse> {
        const response = await api.post('/auth/register', userData)

        if (response.status !== 201) {
            throw new Error('Falha ao realizar cadastro');
        }

        return response.data;
    },

    async login(credentials: LoginCredentials): Promise<AuthResponse> {
        const response = await api.post('/auth/login', credentials);

        if (response.status !== 200) {
            throw new Error('Falha ao efetuar login.');
        }

        return response.data;
    }
}