import { NavBar } from "@/components/NavBar";
import { Button } from "@/components/ui/button";
import { useNavigate } from "react-router-dom";
import { ShieldAlert } from "lucide-react";

export function UnauthorizedPage() {
    const navigate = useNavigate();

    return (
        <>
            <NavBar />
            <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
                <div className="text-center p-8 bg-white rounded-lg shadow-lg max-w-md">
                    <ShieldAlert className="w-20 h-20 mx-auto mb-4 text-red-500" />
                    <h1 className="text-3xl font-bold mb-4 text-gray-800">
                        Acesso Negado
                    </h1>
                    <p className="text-gray-600 mb-6">
                        Você não tem permissão para acessar esta página.
                    </p>
                    <div className="flex gap-4 justify-center">
                        <Button 
                            onClick={() => navigate('/')}
                            className="bg-black text-white hover:bg-gray-800"
                        >
                            Voltar para Home
                        </Button>
                        <Button 
                            onClick={() => navigate(-1)}
                            variant="outline"
                        >
                            Voltar
                        </Button>
                    </div>
                </div>
            </div>
        </>
    );
}