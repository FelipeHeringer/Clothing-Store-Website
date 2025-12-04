import { Category } from "@/components/Category";
import { Hero } from "@/components/Hero";
import { NavBar } from "@/components/NavBar";

import camisetasImg from '@/assets/category/camisetas.png';
import calcasImg from '@/assets/category/calcas.png';
import moletonsImg from '@/assets/category/moletons.png';
import meiasImg from '@/assets/category/meias.png';

export function HomePage() {
    const categories = [
        { image: camisetasImg, label: "CAMISETAS", href: "camisetas" },
        { image: calcasImg, label: "CALÇAS", href: "calcas" },
        { image: moletonsImg, label: "MOLETONS", href: "moletons" },
        { image: meiasImg, label: "MEIAS", href: "meias" }
    ];

    return (
        <>
            <div className="flex bg-transparent text-white flex-col">
                <NavBar />
                <Hero />
                <div className="flex-row w-full">
                    <Category categories={categories} />
                </div>
            </div>
        </>
    )
}