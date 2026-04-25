import { useState } from "react";
import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { useAuth } from "@/View/contexts/AuthContext";

interface LoginData {
    email: string;
    password: string;
}
export function SignInForm({ onSwitchToSignUp }: { onSwitchToSignUp: () => void }) {
    const [formData, setFormData] = useState<LoginData>({
        email: '',
        password: ''
    });
    const [error, setError] = useState('');

    const { login } = useAuth();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
        setFormData(prev => ({
            ...prev,
            [e.target.name]: e.target.value
        }))
    };
    const handleSubmit = async (e: React.FormEvent): Promise<void> => {
        e.preventDefault();
        setError('');

        const result = await login(formData);

        if (!result.success) {
            setError(result.error || 'Erro ao efetuar login.');
        }
    }

    return (
        <>
            <div>
                <div>
                    {error && (
                        <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg">
                            {error}
                        </div>
                    )}
                    <form className="flex flex-col gap-4 bg-transparent p-6  mt-4 text-center">
                        <h1 className="text-[#343434] font-alexandria font-semibold text-3xl">LOGIN</h1>
                        <p className="text-[#343434] font-alexandria font-light text-[22px]">Insira seu email e senha para entrar:</p>
                        <Input
                            type="email"
                            name="email"
                            value={formData.email}
                            placeholder="Email"
                            required
                            onChange={handleChange}
                            className=" w-[571] h-[58] rounded-none border-solid border-1 border-black shadow-md" />
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
                            Login
                        </Button>
                        <p className="text-[#343434] opacity-70 text-[18px] font-alexandria">Ainda não tem uma conta?
                            <button className="text-black font-light text-[18px] font-alexandria underline cursor-pointer" onClick={onSwitchToSignUp}>Cadastre-se</button>
                        </p>

                    </form>
                </div>
            </div>
        </>
    )
}