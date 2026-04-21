import { Route, Routes } from "react-router-dom";
import { AccountPage } from "@/View/pages/AccountPage";
import { HomePage } from "@/View/pages/HomePage";
import { AdminPage } from "@/View/pages/AdminPage";
import { UnauthorizedPage } from "./View/pages/UnauthorizedPage";
import { ProtectedRoute } from "./View/guards/ProtectedRoute";

export function App() {

  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/account" element={<AccountPage />} />
      <Route path="/unauthorized" element={<UnauthorizedPage />} />
      <Route
        path="/admin/*"
        element={
          <ProtectedRoute requireAdmin
            allowedRoles={["ROLE_ADMIN", "ROLE_SUPER_ADMIN"]}>
            <AdminPage />
          </ProtectedRoute>
        }
      />
    </Routes>
  )
}
