import { NavBar } from "@/components/NavBar";
import { SignInForm } from "@/components/SignInForm";
import { SignUpForm } from "@/components/SignUpForm";
import { useState } from "react";

export function AccountPage() {
    const  [showRegister, setShowRegister] = useState(false);
    return (
        <div className="flex flex-col bg-gray-100 min-h-screen items-center justify-center">
            <NavBar />
            {showRegister ? (
                <SignUpForm onSwitchToSignIn={() => setShowRegister(false)} />
            ) : (
                <SignInForm onSwitchToSignUp={() => setShowRegister(true)} />
            )}
        </div>
    );
}