import { AccountDetails } from "@/components/AccountDetails";
import { NavBar } from "@/components/NavBar";
import { SignInForm } from "@/components/SignInForm";
import { SignUpForm } from "@/components/SignUpForm";
import { useAuth } from "@/contexts/AuthContext";
import { useState } from "react";

export function AccountPage() {
    const [showRegister, setShowRegister] = useState(false);
    const { isAuthenticated } = useAuth()
    return (
        <div className="flex flex-col bg-gray-100 min-h-screen items-center justify-center">
            <NavBar />
            {isAuthenticated ? (
                <AccountDetails />
            ) : (
                showRegister ? (
                    <SignUpForm onSwitchToSignIn={() => setShowRegister(false)} />
                ) : (
                    <SignInForm onSwitchToSignUp={() => setShowRegister(true)} />
                )
            )}
        </div>
    );
}