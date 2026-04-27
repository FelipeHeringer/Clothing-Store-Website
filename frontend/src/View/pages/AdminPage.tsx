import { Route, Routes } from "react-router-dom";
import { AdminPanelPage } from "./AdminPanelPage";
import { ProductPage } from "./ProductPage";
import { ProductCreatePage } from "./ProductCreatePage";
import { OrderPage } from "./OrderPage";
import { AdminSidebar } from "../components/admin/AdminSidebar";

export function AdminPage() {

    return (
        <div className="flex min-h-screen bg-gray-50">
            <AdminSidebar />
            <div className="flex-1 overflow-auto">
                <Routes >
                    <Route path="/" element={<AdminPanelPage />} />
                    <Route path="/products" element={<ProductPage />} />
                    <Route path="/products/create" element={<ProductCreatePage />} />
                    <Route path="/orders" element={<OrderPage />} />
                </Routes>
            </div>
        </div>
    );
}