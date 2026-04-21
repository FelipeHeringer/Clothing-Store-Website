import { AdminHeader } from "@/components/admin/AdminHeader"

export function AdminPanelPage() {
    return (
        <div className="p-6">
            <AdminHeader title="Painel" breadcrumb={["Inicio", "Painel"]} />
            <div className="mt-6">
                <p className="text-gray-600">Bem-vindo ao painel de administração</p>
            </div>
        </div>
    );
}