import { authService } from "@/services/authService";
import React, { useContext, useState } from "react";
import { createContext } from "react";

type User = {
    userId: string;
    username: string;
    email: string;
}

type RegisterData = {
    username: string,
    email: string,
    password: string
}

type LoginCredentials = {
    email: string;
    password: string;
};

type AuthContextData = {
    user: User | null;
    register(userData: RegisterData): Promise<{ success: boolean, error?: string }>;
    login(credentials: LoginCredentials): Promise<{ success: boolean, error?: string }>;
    isAuthenticated: boolean;
};

interface AuthProviderProps {
    children: React.ReactNode;
}

const AuthContext = createContext({} as AuthContextData);

export function AuthProvider({ children }: AuthProviderProps) {
    const [user, setUser] = useState<User | null>(null);

    const register = async (userData: RegisterData): Promise<{ success: boolean, error?: string }> => {
        try {
            const response = await authService.register(userData);

            if (response.success && response.accessToken && response.refreshToken && response.safeUser) {
                localStorage.setItem('accessToken', response.accessToken);
                localStorage.setItem('refreshToken', response.refreshToken);
                setUser(response.safeUser)

                return { success: true }
            } else {
                throw new Error(response.message || 'Falha ao realizar o cadastro');
            }
        } catch (error) {
            return {
                success: false,
                error: error instanceof Error ? error.message : 'Falha ao realizar o cadastro.'
            };
        }
    };

    const login = async (credentials: LoginCredentials): Promise<{ success: boolean, error?: string }> => {
        try {
            const response = await authService.login(credentials);

            if (response.success && response.accessToken && response.refreshToken && response.safeUser) {
                localStorage.setItem('accessToken', response.accessToken);
                localStorage.setItem('refreshToken', response.refreshToken);
                setUser(response.safeUser)

                console.log(response)
                return { success: true }
            } else {
                throw new Error(response.message || 'Falha ao efetuar login.');
            }
        } catch (error) {
            return {
                success: false,
                error: error instanceof Error ? error.message : 'Falha ao efetuar login.'
            };
        }
    };

    const value: AuthContextData = {
        user,
        register,
        login,
        isAuthenticated: !!user,
    };
    return (
        <AuthContext.Provider value={value}>
            {children}
        </AuthContext.Provider>
    )
}

// eslint-disable-next-line react-refresh/only-export-components
export function useAuth(): AuthContextData {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within AuthProvider');
    }
    return context;
}