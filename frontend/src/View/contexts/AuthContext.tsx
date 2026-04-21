import React, { createContext, useContext } from "react";
import { useAuthViewModel } from "@/ViewModel/hooks/useAuthViewModel";
import type { AuthViewModel } from "@/ViewModel/hooks/useAuthViewModel";

const AuthContext = createContext<AuthViewModel | null>(null);

interface AuthProviderProps {
    children: React.ReactNode;
}

export function AuthProvider({ children }: AuthProviderProps) {
    const authViewModel = useAuthViewModel();

    return (
        <AuthContext.Provider value={authViewModel}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth(): AuthViewModel {
    const context = useContext(AuthContext);

    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider");
    }

    return context;
}