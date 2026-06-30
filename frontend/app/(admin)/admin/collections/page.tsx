"use client";

import { useState, useEffect } from "react";
import { AdminHeader } from "@/modules/admin/View/components/AdminHeader";
import { collectionService, Collection } from "@/modules/product/Model/services/colectionService";

export default function CollectionsPage() {
  const [collections, setCollections] = useState<Collection[]>([]);
  const [loading, setLoading] = useState(true);
  
  // Form state
  const [collectionName, setCollectionName] = useState("");
  const [description, setDescription] = useState("");
  const [launchDate, setLaunchDate] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    loadCollections();
  }, []);

  const loadCollections = async () => {
    try {
      const data = await collectionService.getAllCollection();
      setCollections(data);
    } catch (error) {
      alert("Erro ao carregar coleções");
    } finally {
      setLoading(false);
    }
  };

  const handleCreateCollection = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!collectionName.trim() || !description.trim() || !launchDate) return;

    setIsSubmitting(true);
    try {
      // Backend expects launchDate in dd-MM-yyyy format
      const [year, month, day] = launchDate.split('-');
      const formattedDate = `${day}-${month}-${year}`;
      
      await collectionService.createCollection(collectionName, description, formattedDate);
      alert("Coleção criada com sucesso!");
      setCollectionName("");
      setDescription("");
      setLaunchDate("");
      loadCollections(); // reload list
    } catch (error: any) {
      alert(error.response?.data?.message || "Erro ao criar coleção");
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="p-6">
      <AdminHeader
        title="Coleções"
        breadcrumb={["Inicio", "Coleções"]}
      />

      <div className="mt-8 bg-white p-6 rounded-lg shadow-sm border border-gray-100">
        <h2 className="text-lg font-medium mb-4">Nova Coleção</h2>
        <form onSubmit={handleCreateCollection} className="space-y-4">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Nome da Coleção
              </label>
              <input
                type="text"
                value={collectionName}
                onChange={(e) => setCollectionName(e.target.value)}
                className="w-full border border-gray-300 rounded-md px-4 py-2 focus:outline-none focus:ring-2 focus:ring-black"
                placeholder="Ex: Verão 2026"
                required
                disabled={isSubmitting}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Data de Lançamento
              </label>
              <input
                type="date"
                value={launchDate}
                onChange={(e) => setLaunchDate(e.target.value)}
                className="w-full border border-gray-300 rounded-md px-4 py-2 focus:outline-none focus:ring-2 focus:ring-black"
                required
                disabled={isSubmitting}
              />
            </div>
          </div>
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">
              Descrição
            </label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="w-full border border-gray-300 rounded-md px-4 py-2 focus:outline-none focus:ring-2 focus:ring-black h-24 resize-none"
              placeholder="Descreva os detalhes desta coleção..."
              required
              disabled={isSubmitting}
            />
          </div>
          
          <div className="flex justify-end">
            <button
              type="submit"
              disabled={isSubmitting}
              className="bg-black text-white px-6 py-2 rounded-md hover:bg-gray-800 disabled:opacity-50"
            >
              {isSubmitting ? "Cadastrando..." : "CADASTRAR COLEÇÃO"}
            </button>
          </div>
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
            ) : collections.length === 0 ? (
              <tr>
                <td colSpan={2} className="px-6 py-4 text-center text-gray-500">Nenhuma coleção encontrada.</td>
              </tr>
            ) : (
              collections.map((col) => (
                <tr key={col.collectionId} className="border-b border-gray-100 last:border-0 hover:bg-gray-50">
                  <td className="px-6 py-4 text-sm text-gray-600">#{col.collectionId}</td>
                  <td className="px-6 py-4 text-sm font-medium">{col.collectionName}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
