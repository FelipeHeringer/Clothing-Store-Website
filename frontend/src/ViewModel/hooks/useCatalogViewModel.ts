import { catalogService, type CatalogFilters, type ProductVariationSummary } from "@/Model/services/catalogService";
import { useCallback, useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";

interface CatalogState {
    variations: ProductVariationSummary[];
    selectedVariation: ProductVariationSummary | null;
    loading: boolean;
    detailLoading: boolean;
    error: string | null;
    totalPages: number;
    totalElements: number;
    currentPage: number;
}

export interface CatalogViewModel extends CatalogState {
    // Filters — these write to the URL so the state is shareable
    categoryId: number | undefined;
    collectionId: number | undefined;
    inStock: boolean | undefined;
    pageSize: number;
    setCategoryId: (id: number | undefined) => void;
    setCollectionId: (id: number | undefined) => void;
    setInStock: (value: boolean | undefined) => void;
    setPage: (page: number) => void;
    setPageSize: (size: number) => void;
    clearFilters: () => void;
    // Detail
    fetchVariationById: (id: number) => Promise<void>;
    clearSelectedVariation: () => void;
}

export function useCatalogViewModel(): CatalogViewModel {
    const [searchParams, setSearchParams] = useSearchParams();

    // --- Derive filter state from URL ---
    const categoryId = searchParams.get("categoryId") ? Number(searchParams.get("categoryId")) : undefined;
    const collectionId = searchParams.get("collectionId") ? Number(searchParams.get("collectionId")) : undefined;
    const inStock = searchParams.get("inStock") ? searchParams.get("inStock") === "true" : undefined;
    const currentPage = searchParams.get("page") ? Number(searchParams.get("page")) : 0;
    const pageSize = searchParams.get("size") ? Number(searchParams.get("size")) : 20;

    const [state, setState] = useState<CatalogState>({
        variations: [],
        selectedVariation: null,
        loading: false,
        detailLoading: false,
        error: null,
        totalPages: 0,
        totalElements: 0,
        currentPage
    });

    useEffect(() => {
        let cancelled = false;

        const fetchVariations = async () => {
            setState(prev => ({ ...prev, loading: true, error: null }));

            try {
                const filters: CatalogFilters = {
                    categoryId,
                    collectionId,
                    inStock,
                    page: currentPage,
                    size: pageSize,
                };

                const data = await catalogService.getVariations(filters);

                if (!cancelled) {
                    setState(prev => ({
                        ...prev,
                        variations: data.content,
                        totalPages: data.page.totalPages,
                        totalElements: data.page.totalElements,
                        currentPage: data.page.number,
                        loading: false,
                    }));
                }
            } catch (err) {
                if (!cancelled) {
                    setState(prev => ({
                        ...prev,
                        loading: false,
                        error: err instanceof Error ? err.message : "Erro ao carregar produtos.",
                    }));
                }
            }
        };

        fetchVariations();

        return () => { cancelled = true };
    }, [categoryId, collectionId, inStock, currentPage, pageSize]);

    // --- URL param setters ---
    const updateParam = useCallback((key: string, value: string | undefined) => {
        setSearchParams(prev => {
            const next = new URLSearchParams(prev);
            if (value === undefined || value === "") {
                next.delete(key);
            } else {
                next.set(key, value);
            }
            // Reset to first page whenever a filter changes
            if (key !== "page") next.delete("page");
            return next;
        });
    }, [setSearchParams]);

    const setCategoryId = useCallback((id: number | undefined) => updateParam("categoryId", id !== undefined ? String(id) : undefined), [updateParam]);
    const setCollectionId = useCallback((id: number | undefined) => updateParam("collectionId", id !== undefined ? String(id) : undefined), [updateParam]);
    const setInStock = useCallback((value: boolean | undefined) => updateParam("inStock", value !== undefined ? String(value) : undefined), [updateParam]);
    const setPage = useCallback((page: number) => updateParam("page", String(page)), [updateParam]);
    const setPageSize = useCallback((size: number) => updateParam("size", String(size)), [updateParam]);

    const clearFilters = useCallback(() => {
        setSearchParams(new URLSearchParams());
    }, [setSearchParams]);

    // --- Detail fetch ---
    const fetchVariationById = useCallback(async (id: number) => {
        setState(prev => ({ ...prev, detailLoading: true, error: null }));
        try {
            const variation = await catalogService.getVariationById(id);
            setState(prev => ({ ...prev, selectedVariation: variation, detailLoading: false }));
        } catch (err) {
            setState(prev => ({
                ...prev,
                detailLoading: false,
                error: err instanceof Error ? err.message : "Erro ao carregar produto.",
            }));
        }
    }, []);

    const clearSelectedVariation = useCallback(() => {
        setState(prev => ({ ...prev, selectedVariation: null }));
    }, []);

    return {
        ...state,
        categoryId,
        collectionId,
        inStock,
        pageSize,
        setCategoryId,
        setCollectionId,
        setInStock,
        setPage,
        setPageSize,
        clearFilters,
        fetchVariationById,
        clearSelectedVariation,
    };
}