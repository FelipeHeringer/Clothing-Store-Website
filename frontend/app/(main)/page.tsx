"use client";

import { Category } from "@/modules/catalog/View/components/Category";
import { Hero } from "@/shared/components/layout/Hero";
import { useNewArrivals } from "@/modules/catalog/ViewModel/hooks/useNewArrivals";
import { useRouter } from "next/navigation";
import { FavoriteIcon } from "@/shared/assets/Icons";
import { Button } from "@/shared/components/ui/button";

const CATEGORY_IDS: Record<string, number> = {
    "CAMISETAS": 1,
    "CALÇAS": 2,
    "MOLETONS": 3,
    "MEIAS": 4,
};

const categories = [
    { image: '/assets/category/camisetas.png', label: "CAMISETAS", href: "/catalog", categoryId: CATEGORY_IDS.CAMISETAS },
    { image: '/assets/category/calcas.png', label: "CALÇAS", href: "/catalog", categoryId: CATEGORY_IDS["CALÇAS"] },
    { image: '/assets/category/moletons.png', label: "MOLETONS", href: "/catalog", categoryId: CATEGORY_IDS.MOLETONS },
    { image: '/assets/category/meias.png', label: "MEIAS", href: "/catalog", categoryId: CATEGORY_IDS.MEIAS },
];

export default function HomePage() {
    const newArrivals = useNewArrivals();
    const router = useRouter();

    return (
        <main className="flex flex-col bg-white text-black font-inter min-h-screen">
            <Hero />
            
            <section className="w-full">
                <Category categories={categories} />
            </section>
            
            <section className="py-24 px-[3%] md:px-[4%]">
                <h2 className="font-archivo-black text-title-mobile md:text-title uppercase tracking-tight text-black mb-12">Novidades</h2>

                {newArrivals.loading && (
                    <div className="flex justify-center py-16">
                        <div className="animate-spin rounded-full h-10 w-10 border-b-2 border-black" />
                    </div>
                )}

                {newArrivals.error && (
                    <p className="text-center text-red-500 py-8">{newArrivals.error}</p>
                )}

                {!newArrivals.loading && !newArrivals.error && newArrivals.variations.length > 0 && (
                    <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-x-4 gap-y-10">
                        {newArrivals.variations.map(v => (
                            <div
                                key={v.variationId}
                                className="flex flex-col gap-3 group cursor-pointer"
                                onClick={() => router.push(`/catalog/variations/${v.variationId}`)}
                            >
                                <div className="aspect-[3/4] bg-gray-100 overflow-hidden relative">
                                    <Button size="icon" variant="ghost"
                                        className="absolute top-2 right-2 z-10 opacity-0 group-hover:opacity-100 transition-opacity bg-white/80 hover:bg-white" onClick={(e) => { e.stopPropagation(); }}>
                                        <FavoriteIcon width={20} height={20} color="#333" />
                                    </Button>
                                    <div className="w-full h-full flex items-center justify-center text-gray-400 text-sm">
                                        {/* Ideally replace with an actual image component */}
                                        {v.skuCode}
                                    </div>
                                    <div className="absolute inset-0 bg-black/5 opacity-0 group-hover:opacity-100 transition-opacity" />
                                </div>
                                <div className="flex flex-col">
                                    <p className="font-semibold text-sm uppercase text-black group-hover:underline decoration-1 underline-offset-4">
                                        {v.productName}
                                    </p>
                                    <p className="text-sm font-medium mt-1 text-gray-500">
                                        R$ {v.price.toFixed(2).replace(".", ",")}
                                    </p>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </section>
        </main>
    )
}
