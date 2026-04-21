import { jwtDecode } from "jwt-decode";

interface TokenPayload  {
    roles: string[];
    sub: string;
    iat: number;
    exp: number;
}

export const tokenUtil = {

    getAccessToken(): string | null {
        return localStorage.getItem("accessToken");
    },

    decodeToken(): TokenPayload | null {
        const accessToken = this.getAccessToken();

        if(!accessToken) return null;

        try{
            return jwtDecode<TokenPayload>(accessToken);
        } catch (error) {
            console.error("Token invalido: ", error);
            return null;
        }
    },

    getRoles(): string[] | null {
        const payload = this.decodeToken();
        return payload?.roles ?? null;
    },

    isAdmin(): boolean {
        const allowedRoles = ["ROLE_ADMIN", "ROLE_SUPER_ADMIN"]
        const userRoles = this.getRoles();
        const isAdmin = userRoles?.some(userRole => allowedRoles.includes(userRole)) ?? false;   
        return isAdmin;
    }
}