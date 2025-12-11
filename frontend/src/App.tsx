import { Route, Routes } from "react-router-dom";
import { AccountPage } from "@/pages/AccountPage";
import { HomePage } from "@/pages/HomePage";
import { AdminPage } from "@/pages/AdminPage";
import { ProtectedRoute } from "./utils/ProtectedRoute";
import { UnauthorizedPage } from "./pages/UnauthorizedPage";

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
