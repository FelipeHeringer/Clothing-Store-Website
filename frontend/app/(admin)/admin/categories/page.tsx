"use client";

import { useState, useEffect } from "react";
import { AdminHeader } from "@/modules/admin/View/components/AdminHeader";
import { categoryService, Category } from "@/modules/product/Model/services/categoryService";

export default function CategoriesPage() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [loading, setLoading] = useState(true);
  const [newCategoryName, setNewCategoryName] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    loadCategories();
  }, []);

  const loadCategories = async () => {
    try {
      const data = await categoryService.getAllCategories();
      setCategories(data);
    } catch (error) {
      alert("Erro ao carregar categorias");
    } finally {
      setLoading(false);
    }
  };

  const handleCreateCategory = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!newCategoryName.trim()) return;

    setIsSubmitting(true);
    try {
      await categoryService.createCategory(newCategoryName);
      alert("Categoria criada com sucesso!");
      setNewCategoryName("");
      loadCategories(); // reload list
    } catch (error: any) {
      alert(error.response?.data?.message || "Erro ao criar categoria");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="p-6">
      <AdminHeader
        title="Categorias"
        breadcrumb={["Inicio", "Categorias"]}
      />

      <div className="mt-8 bg-white p-6 rounded-lg shadow-sm border border-gray-100">
        <h2 className="text-lg font-medium mb-4">Nova Categoria</h2>
        <form onSubmit={handleCreateCategory} className="flex gap-4 items-end">
          <div className="flex-1">
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Nome da Categoria
            </label>
            <input
              type="text"
              value={newCategoryName}
              onChange={(e) => setNewCategoryName(e.target.value)}
              className="w-full border border-gray-300 rounded-md px-4 py-2 focus:outline-none focus:ring-2 focus:ring-black"
              placeholder="Ex: Camisetas"
              required
              disabled={isSubmitting}
            />
          </div>
          <button
            type="submit"
            disabled={isSubmitting}
            className="bg-black text-white px-6 py-2 rounded-md hover:bg-gray-800 disabled:opacity-50 h-[42px]"
          >
            {isSubmitting ? "Cadastrando..." : "CADASTRAR CATEGORIA"}
          </button>
        </form>
      </div>

      <div className="mt-8 bg-white rounded-lg shadow-sm border border-gray-100 overflow-hidden">
        <table className="w-full text-left border-collapse">
          <thead>
            <tr className="bg-gray-50 border-b border-gray-200">
              <th className="px-6 py-3 text-sm font-medium text-gray-500">ID</th>
              <th className="px-6 py-3 text-sm font-medium text-gray-500">NOME</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr>
                <td colSpan={2} className="px-6 py-4 text-center text-gray-500">Carregando...</td>
              </tr>
            ) : categories.length === 0 ? (
              <tr>
                <td colSpan={2} className="px-6 py-4 text-center text-gray-500">Nenhuma categoria encontrada.</td>
              </tr>
            ) : (
              categories.map((cat) => (
                <tr key={cat.categoryId} className="border-b border-gray-100 last:border-0 hover:bg-gray-50">
                  <td className="px-6 py-4 text-sm text-gray-600">#{cat.categoryId}</td>
                  <td className="px-6 py-4 text-sm font-medium">{cat.categoryName}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
