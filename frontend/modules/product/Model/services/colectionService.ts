import { tokenUtil } from "@/lib/tokenUtil";
import { api } from "@/infrastructure/api/api";

export interface Collection {
    collectionId: number;
    collectionName: string;
    description?: string;
    launchDate?: string;
}

export const collectionService = {
    async getAllCollection(): Promise<Collection[]> {
        const token = tokenUtil.getAccessToken();
        const response = await api.get('admin/collections', {
            headers: {
                Authorization: `Bearer ${token}`
            },
        })

        return response.data.collections;
    },

    async createCollection(collectionName: string, description: string, launchDate: string): Promise<Collection> {
        const token = tokenUtil.getAccessToken();
        const response = await api.post('admin/collections', 
            { collectionName, description, launchDate },
            {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            }
        );
        return response.data.collections[0];
    }
}
