import { useState } from "react";
import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { useAuth } from "@/contexts/AuthContext";

interface RegisterData {
    username: string;
    email: string;
    password: string;
}

export function SignUpForm({ onSwitchToSignIn }: { onSwitchToSignIn: () => void }) {
    const [formData, setFormData] = useState<RegisterData>({
        username: '',
        email: '',
        password: ''
    });
    const [error, setError] = useState('');

    const { register, isAuthenticated } = useAuth();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
        setFormData(prev => ({
            ...prev,
            [e.target.name]: e.target.value
        }))
    };

    const handleSubmit = async (e: React.FormEvent): Promise<void> => {
        e.preventDefault();
        setError('');

        const result = await register(formData);

        if (!result.success) {
            setError(result.error || 'Erro ao efetuar login.');
        }
    }

    if(isAuthenticated) {
        return (
            <div className="flex h-screen items-center justify-center">
                <h1>Usuário autenticado</h1>
            </div>
        )
    }

    return (
        <>
            <style>
                @import url('https://fonts.googleapis.com/css2?family=Alexandria:wght@400;600&display=swap');
            </style>
            <div>
                <div>
                    {error && (
                        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg">
                            {error}
                        </div>
                    )}
                    <form className="flex flex-col gap-4 bg-transparent p-6  shadow-md mt-4 text-center">
                        <h1 className="text-black font-['Alexandria'] font-semibold text-[32px]">Cadastro</h1>
                        <p className="text-black font-['Alexandria'] font-light text-[22px]">Insira seu email e senha para criar uma conta:</p>
                        <Input
                            type="username"
                            name="username"
                            value={formData.username}
                            placeholder="Username"
                            required
                            onChange={handleChange}
                            className="w-[571] h-[58] rounded-none border-solid border-1 border-black" />
                        <Input
                            type="email"
                            name="email"
                            value={formData.email}
                            placeholder="Email"
                            required
                            onChange={handleChange}
                            className=" w-[571] h-[58] rounded-none border-solid border-1 border-black" />
                        <Input
                            type="password"
                            name="password"
                            value={formData.password}
                            placeholder="Senha"
                            required
                            onChange={handleChange}
                            className="w-[571] h-[58] rounded-none border-solid border-1 border-black" />

                        <Button
                            type="button"
                            variant="link"
                            onClick={handleSubmit}
                            className="bg-black text-white rounded-none opacity-100 w-[571] h-[58]">
                            Cadastrar
                        </Button>
                        <p className="text-[#343434] opacity-70 text-[18px] font-['Alexandria']">Já tem uma conta?
                            <button className="text-black font-light text-[18px] font-['Alexandria'] underline cursor-pointer" onClick={onSwitchToSignIn}>Entrar</button>
                        </p>
                    </form>
                </div>
            </div>
        </>
    )
}