import { tokenUtil } from "@/lib/tokenUtil";
import { api } from "./api";

export interface Collection {
    collectionId: number;
    collectionName: string;
}

export const collectionService = {
    async getAllCollection(): Promise<Collection[]> {
        const token = tokenUtil.getAccessToken();
        const response = await api.get('admin/collections', {
            headers : {
                Authorization: `Bearer ${token}`
            },
        })

        return response.data.collections;
    }
}