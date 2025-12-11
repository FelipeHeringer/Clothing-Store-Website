import { useAuth } from "@/contexts/AuthContext";
import { Navigate } from "react-router-dom";
import { tokenUtil } from "./tokenUtil";

interface ProtectedRouteProps {
    children: React.ReactNode;
    requireAdmin?: boolean;
    allowedRoles?: string[];
}

export function ProtectedRoute({ children, requireAdmin = false, allowedRoles }: ProtectedRouteProps) {
    const { isAuthenticated, loading } = useAuth();

    if (loading) {
        console.log('⏳ Waiting for auth to initialize...');
        return (
            <div className="flex h-screen items-center justify-center bg-gray-100">
                <div className="text-center">
                    <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-black mx-auto mb-4"></div>
                    <div className="text-lg font-['Alexandria']">Verificando autenticação...</div>
                </div>
            </div>
        );
    }

    if (!isAuthenticated) {
        return <Navigate to="/account" replace />
    }

    const accessToken = tokenUtil.getAccessToken();
    if (!accessToken) {
        return <Navigate to="/account" replace />
    }

    const payload = tokenUtil.decodeToken();
    if (!payload) {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        return <Navigate to="/account" replace />
    }

    if (payload.exp * 1000 < Date.now()) {
        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');
        return <Navigate to="/login" replace />;
    }

    if (allowedRoles && allowedRoles.length > 0) {
        const userRoles = tokenUtil.getRoles();

        if (!userRoles) {
            return <Navigate to="/unauthorized" replace />
        }

        const hasAllowedRoles = userRoles.some(useRole =>
            allowedRoles.includes(useRole))

        if (!hasAllowedRoles) {
            return <Navigate to="/unauthorized" replace />
        }
    }

    if (requireAdmin && !tokenUtil.isAdmin()) {
        return <Navigate to="/unauthorized" replace />;
    }

    return <>{children}</>;
}