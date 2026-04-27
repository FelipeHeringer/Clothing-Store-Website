import { api } from "./api";
import { tokenUtil } from "@/lib/tokenUtil";
import type { Product } from "../models/product";
import type { ProductRequest } from "../models/productRequest";

export const productService = {
  async getAllProducts(): Promise<Product[]> {

    const token = tokenUtil.getAccessToken();
    const response = await api.get("/admin/products", {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data;
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

  async createProduct(product: ProductRequest): Promise<Product> {
    const token = tokenUtil.getAccessToken();
    const response = await api.post('/admin/products', product , {
      headers: {
        Authorization: `Bearer ${token}`
      },
    });

    return response.data.product
  }
};

