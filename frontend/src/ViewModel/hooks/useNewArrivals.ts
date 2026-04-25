import { useState, useEffect } from "react";
import { catalogService, type ProductVariationSummary } from "@/Model/services/catalogService";

interface NewArrivalsState {
    variations: ProductVariationSummary[];
    loading: boolean;
    error: string | null;
}

const NEW_ARRIVALS_SIZE = 10;

export function useNewArrivals(): NewArrivalsState {
    const [state, setState] = useState<NewArrivalsState>({
        variations: [],
        loading: false,
        error: null,
    });

    useEffect(() => {
        let cancelled = false;

        const fetch = async () => {
            setState(prev => ({ ...prev, loading: true, error: null }));
            try {
                const data = await catalogService.getVariations({
                    inStock: true,
                    size: NEW_ARRIVALS_SIZE,
                    sort: "variationId,desc",
                });
                if (!cancelled) {
                    setState({ variations: data.content, loading: false, error: null });
                }
            } catch (err) {
                if (!cancelled) {
                    setState({
                        variations: [],
                        loading: false,
                        error: err instanceof Error ? err.message : "Erro ao carregar novidades.",
                    });
                }
            }
        };

        fetch();
        return () => { cancelled = true; };
    }, []);

    return state;
}