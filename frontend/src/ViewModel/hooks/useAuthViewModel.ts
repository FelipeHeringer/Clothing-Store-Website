import { useState, useEffect } from "react";
import { authService } from "@/Model/services/authService";
import { tokenUtil } from "@/lib/tokenUtil";

export interface User {
    userId: string;
    username: string;
    email: string;
}

interface LoginCredentials {
    email: string;
    password: string;
}

interface RegisterData {
    username: string;
    email: string;
    password: string;
}

interface AuthResult {
    success: boolean;
    error?: string;
}

export interface AuthViewModel {
    user: User | null;
    isAuthenticated: boolean;
    loading: boolean;
    login: (credentials: LoginCredentials) => Promise<AuthResult>;
    register: (userData: RegisterData) => Promise<AuthResult>;
    logout: () => void;
}

export function useAuthViewModel(): AuthViewModel {
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const restoreSession = () => {
            try {
                const accessToken = tokenUtil.getAccessToken();
                const storedUser = localStorage.getItem("user");

                if (accessToken && storedUser) {
                    const payload = tokenUtil.decodeToken();
                    const isTokenValid = payload && payload.exp * 1000 > Date.now();

                    if (isTokenValid) {
                        setUser(JSON.parse(storedUser));
                    } else {
                        clearSession();
                    }
                }
            } catch (error) {
                console.error("Failed to restore session:", error);
                clearSession();
            } finally {
                setLoading(false);
            }
        };

        restoreSession();
    }, []);

    const persistSession = (accessToken: string, refreshToken: string, user: User) => {
        localStorage.setItem("accessToken", accessToken);
        localStorage.setItem("refreshToken", refreshToken);
        localStorage.setItem("user", JSON.stringify(user));
        setUser(user);
    };

    const clearSession = () => {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("refreshToken");
        localStorage.removeItem("user");
        setUser(null);
    };

    const login = async (credentials: LoginCredentials): Promise<AuthResult> => {
        try {
            const response = await authService.login(credentials);

            if (response.success && response.accessToken && response.refreshToken && response.safeUser) {
                persistSession(response.accessToken, response.refreshToken, response.safeUser);
                return { success: true };
            }

            throw new Error(response.message || "Falha ao efetuar login.");
        } catch (error) {
            return {
                success: false,
                error: error instanceof Error ? error.message : "Falha ao efetuar login.",
            };
        }
    };

    const register = async (userData: RegisterData): Promise<AuthResult> => {
        try {
            const response = await authService.register(userData);

            if (response.success && response.accessToken && response.refreshToken && response.safeUser) {
                persistSession(response.accessToken, response.refreshToken, response.safeUser);
                return { success: true };
            }

            throw new Error(response.message || "Falha ao realizar o cadastro.");
        } catch (error) {
            return {
                success: false,
                error: error instanceof Error ? error.message : "Falha ao realizar o cadastro.",
            };
        }
    };

    const logout = () => {
        clearSession();
    };

    return {
        user,
        isAuthenticated: !!user,
        loading,
        login,
        register,
        logout,
    };
}