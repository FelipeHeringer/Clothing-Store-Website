import { useNavigate } from "react-router-dom";
import { Button } from "./ui/button";
import { FavoriteIcon } from "@/assets/Icons";

interface ProductItemProps {
    variationId: number;
    productName: string;
    price: number;
    productImages?: string[];
}

export function ProductItem({ variationId, productName, price }: ProductItemProps) {
    const navigate = useNavigate();

    return (
        <div key={variationId} onClick={() => navigate(`/catalog/variations/${variationId}`)}
            className="flex flex-col gap-1">
            <div className="aspect-[3/4] bg-gray-100 overflow-hidden mb-3 relative">
                <Button size={"icon"} variant={"ghost"}
                    className="absolute top-3 right-3 flex items-center justify-center" onClick={() => { }}>
                    <FavoriteIcon width={32} height={32} color="#C8C8C8" className="absolute top-2.5 right-2.5" />
                </Button>
                <div className="w-full h-full flex items-center justify-center text-gray-400 text-sm">
                    {productName}
                </div>
            </div>
            <p className="font-semibold text-sm uppercase font-alexandria text-[#353535] group-hover:underline">
                {productName}
            </p>
            <p className="text-sm font-medium mt-1 font-alexandria text-[#6D6D6D] ">
                R$ {price.toFixed(2).replace(".", ",")}
            </p>
        </div>
    )
}