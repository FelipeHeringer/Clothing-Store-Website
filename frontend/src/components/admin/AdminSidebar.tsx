import { LayoutDashboard, Package, FileText, ChevronDown, ChevronUp } from "lucide-react";
import { useState, useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import { cn } from "@/lib/utils";
import { categoryService } from "@/services/categoryService";
import type { Category } from "@/services/categoryService";

export function AdminSidebar() {
  const location = useLocation();
  const [isCategoryOpen, setIsCategoryOpen] = useState(true);
  const [categories, setCategories] = useState<Category[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<number | null>(5);

  useEffect(() => {
    categoryService.getAllCategories().then(setCategories);
  }, []);

  return (
    <div className="w-64 bg-white min-h-screen border-r border-gray-200 flex flex-col">
      {/* Logo and Company Name */}
      <div className="p-6 border-b border-gray-200">
        <div className="flex items-center gap-2">
          <div className="w-8 h-8 bg-black rounded"></div>
          <span className="font-semibold text-lg">vérticecompany</span>
        </div>
      </div>

      {/* Navigation */}
      <nav className="flex-1 p-4 space-y-2">
        <Link
          to="/admin"
          className={cn(
            "flex items-center gap-3 px-4 py-3 rounded-lg transition-colors",
            location.pathname === "/admin" || location.pathname === "/admin/"
              ? "bg-blue-50 text-blue-600"
              : "text-gray-700 hover:bg-gray-50"
          )}
        >
          <LayoutDashboard className="w-5 h-5" />
          <span className="font-medium">PAINEL</span>
        </Link>

        <Link
          to="/admin/products"
          className={cn(
            "flex items-center gap-3 px-4 py-3 rounded-lg transition-colors",
            location.pathname === "/admin/products"
              ? "bg-blue-50 text-blue-600"
              : "text-gray-700 hover:bg-gray-50"
          )}
        >
          <Package className="w-5 h-5" />
          <span className="font-medium">TODOS PRODUTOS</span>
        </Link>

        <Link
          to="/admin/orders"
          className={cn(
            "flex items-center gap-3 px-4 py-3 rounded-lg transition-colors",
            location.pathname === "/admin/orders"
              ? "bg-blue-50 text-blue-600"
              : "text-gray-700 hover:bg-gray-50"
          )}
        >
          <FileText className="w-5 h-5" />
          <span className="font-medium">ORDEM DOS PEDIDOS</span>
        </Link>

        {/* Category Filter */}
        <div className="pt-4">
          <button
            onClick={() => setIsCategoryOpen(!isCategoryOpen)}
            className="flex items-center justify-between w-full px-4 py-3 text-gray-700 hover:bg-gray-50 rounded-lg transition-colors"
          >
            <span className="font-medium">Categoria</span>
            {isCategoryOpen ? (
              <ChevronUp className="w-4 h-4" />
            ) : (
              <ChevronDown className="w-4 h-4" />
            )}
          </button>

          {isCategoryOpen && (
            <div className="mt-2 space-y-1">
              {categories.map((category) => (
                <button
                  key={category.categoryId}
                  onClick={() => setSelectedCategory(category.categoryId)}
                  className={cn(
                    "w-full flex items-center justify-between px-4 py-2 rounded-lg transition-colors text-left",
                    selectedCategory === category.categoryId
                      ? "bg-blue-50 text-blue-600"
                      : "text-gray-700 hover:bg-gray-50"
                  )}
                >
                  <span>{category.categoryName}</span>
                </button>
              ))}
            </div>
          )}
        </div>
      </nav>
    </div>
  );
}

