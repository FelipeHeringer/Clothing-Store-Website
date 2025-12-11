import { api } from "./api";
import { tokenUtil } from "@/utils/tokenUtil";

export interface Product {
  productId: number;
  name: string;
  description: string;
  price: number;
  category?: {
    id: number;
    name: string;
  };
  collection?: {
    id: number;
    name: string;
  };
  score?: number;
}

export interface ProductWithMetrics extends Product {
  type: string;
  sales: number;
  stock: number;
  imageUrl?: string;
}

export const productService = {
  async getAllProducts(): Promise<Product[]> {
    // Note: This endpoint doesn't exist yet in the backend
    /*
    const token = tokenUtil.getAccessToken();
    const response = await api.get("/admin/products", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
    */
    return [];
  },

  async getProductById(id: number): Promise<Product> {
    const token = tokenUtil.getAccessToken();
    const response = await api.get(`/admin/products/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data.product;
  },

  async createProduct(product: Product): Promise<Product> {
    const token = tokenUtil.getAccessToken();
    const response = await api.post('/admin/products', product , {
      headers: {
        Authorization: `Bearer ${token}`
      },
    });

    return response.data.product
  }
};

