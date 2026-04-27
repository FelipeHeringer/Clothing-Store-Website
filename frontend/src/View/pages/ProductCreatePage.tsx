import { useNavigate } from "react-router-dom";
import { AdminHeader } from "../components/admin/AdminHeader";
import { ProductCreateForm } from "../components/ProductCreateForm";

export function ProductCreatePage() {
    const navigate = useNavigate();

    const handleSubmit = () => {
        navigate("/admin/products");
    }

    const handleCancel = () => {
        navigate("/admin/products");   
    }

    return (
        <div className="p-6 bg-[#C8C8C8] min-h-screen">
            <AdminHeader
                title="Detalhes do Produto"
                breadcrumb={["Inicio", "Produtos", "Cadastrar Produto"]}
            />
            <div className="mt-6">
                <ProductCreateForm onSubmit={handleSubmit} onCancel={handleCancel}/>
            </div>
        </div>
    );
}