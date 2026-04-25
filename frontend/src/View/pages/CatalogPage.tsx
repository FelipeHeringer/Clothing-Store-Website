import { useCatalogViewModel } from "@/ViewModel/hooks/useCatalogViewModel"
import { ProductItem } from "../components/ProductItem";
import { NavBar } from "../components/NavBar";

export function CatalogPage() {
    const { variations } = useCatalogViewModel();

    return (
        <div className="flex flex-col gap-3">
            <NavBar />
            <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
                {variations.map(v => (
                    <ProductItem variationId={v.variationId} productName={v.productName} price={v.price}/>
                ))}
            </div>
        </div>
    )
}