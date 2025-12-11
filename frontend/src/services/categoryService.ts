import { tokenUtil } from "@/utils/tokenUtil";
import { api } from "./api";

export interface Category {
    categoryId: number;
    categoryName: string
}

export const categoryService = {

    async getAllCategories(): Promise<Category[]> {
        const token = tokenUtil.getAccessToken();
        const response = await api.get('/admin/categories', {
            headers: {
                Authorization: `Bearer ${token}`
            },
        });
        
        return response.data.categories;
    }
}