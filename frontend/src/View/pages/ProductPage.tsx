import { useNavigate } from "react-router-dom";
import { AdminHeader } from "../components/admin/AdminHeader";

export function ProductPage() {
    const navigate = useNavigate();

    const handleCreateProduct = () => {
        navigate("/admin/products/create");
    }

    return (
        <div className="p-6">
            <AdminHeader
                title="Todos os produtos"
                breadcrumb={["Inicio", "Todos os produtos"]}
                showCreateButton
                createButtonLabel="CADASTRAR PRODUTO"
                onCreateClick={handleCreateProduct}
            />
        </div>
    );
}