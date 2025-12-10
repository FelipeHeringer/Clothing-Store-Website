import { AdminHeader } from "@/components/admin/AdminHeader";

export function ProductPage() {

    const handleCreateProduct = () => {
        console.log("Create product clicked")
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
            {/* {loading ? (
                <div className="flex items-center justify-center h-64">
                    <div className="text-gray-500">Carregando produtos...</div>
                </div>
            ) : (
                <ProductGrid products={products} />
            )} */}
        </div>
    );
}