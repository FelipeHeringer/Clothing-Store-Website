import { api } from "./api";

export interface ProductVariationSummary {
    variationId: number;
    productId: number;
    productName: string;
    productDescription: string;
    price: number;
    score: number;
    categoryName: string;
    collectionName: string;
    color: string;
    size: string;
    skuCode: string;
    stock: number;
    inStock: boolean;
}

export interface CatalogFilters {
    categoryId?: number;
    collectionId?: number;
    colorId?: number;
    sizeId?: number;
    inStock?: boolean;
    page?: number;
    size?: number;
    sort?: string;
}

export interface PagedVariations {
    content: ProductVariationSummary[];
    page: {
        size: number;
        number: number;
        totalElements: number;
        totalPages: number;
    }
}

export const catalogService = {
    async getVariations(filters: CatalogFilters = {}): Promise<PagedVariations> {
        const params = new URLSearchParams();

        if (filters.categoryId !== undefined) params.set("categoryId", String(filters.categoryId));
        if (filters.collectionId !== undefined) params.set("collectionId", String(filters.collectionId));
        if (filters.colorId !== undefined) params.set("colorId", String(filters.colorId));
        if (filters.sizeId !== undefined) params.set("sizeId", String(filters.sizeId));
        if (filters.inStock !== undefined) params.set("inStock", String(filters.inStock));
        if (filters.page !== undefined) params.set("page", String(filters.page));
        if (filters.size !== undefined) params.set("size", String(filters.size));
        if (filters.sort) params.set("sort", filters.sort);

        const response = await api.get(`/catalog/variations?${params.toString()}`);

        return response.data;
    },

    async getVariationById(variationId: number): Promise<ProductVariationSummary> {
        const response = await api.get(`/catalog/variations/${variationId}`);
        return response.data
    }
}