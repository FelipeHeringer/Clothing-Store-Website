import { tokenUtil } from "@/lib/tokenUtil";
import { api } from "@/infrastructure/api/api";

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
    },

    async createCategory(categoryName: string): Promise<Category> {
        const token = tokenUtil.getAccessToken();
        const response = await api.post('/admin/categories', 
            { categoryName },
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
        return response.data.categories[0];
    }
}
