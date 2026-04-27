import { ChevronDown, ImagePlus, X } from "lucide-react";
import { DropdownMenuTrigger, DropdownMenuContent, DropdownMenuItem, DropdownMenu } from "./ui/dropdown-menu";
import { Input } from "./ui/input";
import { Label } from "./ui/label";
import { Textarea } from "./ui/textarea";
import { useEffect, useRef, useState } from "react";
import { categoryService, type Category } from "@/Model/services/categoryService";
import { collectionService, type Collection } from "@/Model/services/colectionService";
import { Button } from "./ui/button";
import { productService } from "@/Model/services/productService";

interface ProductFormData {
    name: string;
    description: string;
    categoryId: number | null;
    collectionId: number | null;
    price: string;
    mainImage: File | null;
    galleryImages: File[];
}

interface ProductCreateFormProps {
    onSubmit: () => void;
    onCancel: () => void;
}

export function ProductCreateForm({ onSubmit, onCancel }: ProductCreateFormProps) {
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [categories, setCategories] = useState<Category[]>([]);
    const [collections, setCollections] = useState<Collection[]>([]);


    const mainImageInputRef = useRef<HTMLInputElement>(null);
    const galleryInputRef = useRef<HTMLInputElement>(null);

    const [formData, setFormData] = useState<ProductFormData>({
        name: "",
        description: "",
        categoryId: null,
        collectionId: null,
        price: "",
        mainImage: null,
        galleryImages: [],
    });

    const [selectedCategory, setSelectedCategory] = useState<string | null>(null);
    const [selectedCollection, setSelectedCollection] = useState<string | null>(null);

    const [mainImagePreview, setMainImagePreview] = useState<string | null>(null);
    const [galleryPreviews, setGalleryPreviews] = useState<string[]>([]);

    useEffect(() => {
        categoryService.getAllCategories().then(setCategories);
        collectionService.getAllCollection().then(setCollections);
    }, []);

    const handleFieldChange = (field: keyof ProductFormData, value: string | number | null) => {
        setFormData(prev => ({
            ...prev,
            [field]: value,
        }));
    }

    const handleCategorySelect = (category: Category) => {
        setSelectedCategory(category.categoryName);
        handleFieldChange("categoryId", category.categoryId);
    }

    const handleCollectionSelect = (collection: Collection) => {
        setSelectedCollection(collection.collectionName);
        handleFieldChange("collectionId", collection.collectionId);
    }

    const handleMainImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (!file) return;
        setFormData(prev => ({ ...prev, mainImage: file }));
        setMainImagePreview(URL.createObjectURL(file));
    };

    const handleGalleryImagesChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const files = Array.from(e.target.files ?? []);
        if (!files.length) return;
        setFormData(prev => ({ ...prev, galleryImages: [...prev.galleryImages, ...files] }));
        setGalleryPreviews(prev => [...prev, ...files.map(f => URL.createObjectURL(f))]);
    };

    const removeGalleryImage = (index: number) => {
        setFormData(prev => ({
            ...prev,
            galleryImages: prev.galleryImages.filter((_, i) => i !== index),
        }));
        setGalleryPreviews(prev => prev.filter((_, i) => i !== index));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError(null);

        if (!formData.name || !formData.categoryId || !formData.collectionId || !formData.price) {
            setError("Preencha todos os campos obrigatórios.");
            return;
        }

        try {
            setIsSubmitting(true);

            const productRequest = {
                name: formData.name,
                description: formData.description,
                categoryId: formData.categoryId,
                collectionId: formData.collectionId,
                price: parseFloat(formData.price),
                score: 0,
            }
            await productService.createProduct(productRequest);
            onSubmit();
        } catch (err) {
            setError(err instanceof Error ? err.message : "Erro ao cadastrar produto.");
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col gap-12 bg-white p-6 rounded-2xl shadow-lg max-w-250 max-h-250">
            {error && (
                <div className="bg-red-50 border border-red-200 text-red-700 px-4 py-3 rounded-lg text-sm">
                    {error}
                </div>
            )}

            <div className="flex gap-12">
                <div className="flex flex-col lg:flex-row gap-8">
                    <div className="flex flex-col gap-5 flex-1 max-w-[520px]">
                        <Label className="font-alexandria" htmlFor="name">Nome do Produto</Label>
                        <Input id="name" type="text" className="md:w-130 md:h-11" value={formData.name} onChange={e => handleFieldChange("name", e.target.value)} />
                        <Label className="font-alexandria" htmlFor="description">Descrição</Label>
                        <Textarea id="description" className="md:w-130 md:h-24" value={formData.description} onChange={e => handleFieldChange("description", e.target.value)} />
                        <Label className="font-alexandria" htmlFor="category">Categoria</Label>
                        <DropdownMenu>
                            <DropdownMenuTrigger className="w-full md:w-130 h-11 border rounded-md text-left px-3 flex items-center justify-between">
                                {selectedCategory || "Selecione uma categoria"}
                                <ChevronDown className="w-4 h-4 ml-auto" />
                            </DropdownMenuTrigger>
                            <DropdownMenuContent className="w-full md:w-130">
                                {categories.map(c => (
                                    <DropdownMenuItem key={c.categoryId} onSelect={() => handleCategorySelect(c)}>
                                        {c.categoryName}
                                    </DropdownMenuItem>
                                ))}
                            </DropdownMenuContent>
                        </DropdownMenu>
                        <div className="flex gap-4">
                            <div className="flex flex-col gap-4">
                                <Label className="font-alexandria" htmlFor="collection">Coleção</Label>
                                <DropdownMenu>
                                    <DropdownMenuTrigger className="w-full md:w-60 h-11 border rounded-md text-left px-3 flex items-center justify-between">
                                        {selectedCollection || "Selecione uma coleção"}
                                        <ChevronDown className="w-4 h-4 ml-auto" />
                                    </DropdownMenuTrigger>
                                    <DropdownMenuContent className="w-full md:w-60">
                                        {collections.map(c => (
                                            <DropdownMenuItem key={c.collectionId} onSelect={() => handleCollectionSelect(c)}>
                                                {c.collectionName}
                                            </DropdownMenuItem>
                                        ))}
                                    </DropdownMenuContent>
                                </DropdownMenu>
                            </div>
                            <div className="flex flex-col gap-4">
                                <Label className="font-alexandria" htmlFor="price">Preço</Label>
                                <Input id="price" type="number" className="md:w-60 md:h-11" placeholder="Ex: 109.90" value={formData.price} onChange={e => handleFieldChange("price", e.target.value)} />
                            </div>
                        </div>
                    </div>
                </div>

                {/* Right column — images */}
                <div className="flex flex-col gap-6 lg:w-90">
                    <div className="flex flex-col gap-2">
                        <div className="flex flex-col gap-2">
                            <button
                                type="button"
                                onClick={() => mainImageInputRef.current?.click()}
                                className="w-full aspect-square border-2 border-dashed border-gray-300 rounded-lg bg-gray-50 hover:bg-gray-100 transition-colors flex flex-col items-center justify-center gap-2 overflow-hidden relative"
                            >
                                {mainImagePreview ? (
                                    <img
                                        src={mainImagePreview}
                                        alt="Preview"
                                        className="w-full h-full object-cover"
                                    />
                                ) : (
                                    <>
                                        <ImagePlus className="w-10 h-10 text-gray-400" />
                                        <span className="text-sm text-gray-500 font-alexandria">Adicionar imagem</span>
                                    </>
                                )}
                            </button>
                            <input
                                ref={mainImageInputRef}
                                type="file"
                                accept="image/*"
                                className="hidden"
                                onChange={handleMainImageChange}
                            />
                            <div className="flex flex-col gap-2">
                                <Label className="font-medium text-gray-700">Galeria do produto</Label>
                                <div className="w-full min-h-[150px] border-2 border-dashed border-gray-300 rounded-lg bg-gray-50 p-3 flex flex-wrap gap-2">
                                    {galleryPreviews.map((src, i) => (
                                        <div key={i} className="relative w-30 h-30 rounded overflow-hidden group">
                                            <img src={src} alt="" className="w-full h-full object-cover" />
                                            <button
                                                type="button"
                                                onClick={() => removeGalleryImage(i)}
                                                className="absolute inset-0 bg-black/40 opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center"
                                            >
                                                <X className="w-4 h-4 text-white" />
                                            </button>
                                        </div>
                                    ))}
                                    <button
                                        type="button"
                                        onClick={() => galleryInputRef.current?.click()}
                                        className="w-30 h-30 border-2 border-dashed border-gray-300 rounded flex flex-col items-center justify-center gap-1 text-gray-400 hover:bg-gray-100 transition-colors"
                                    >
                                        <ImagePlus className="w-5 h-5" />
                                        <span className="text-xs">Adicionar</span>
                                    </button>
                                </div>
                                <input
                                    ref={galleryInputRef}
                                    type="file"
                                    accept="image/*"
                                    multiple
                                    className="hidden"
                                    onChange={handleGalleryImagesChange}
                                />
                            </div>
                        </div>
                    </div>
                </div>
            </div>


            <div className="flex gap-3 pt-2 relative r-0">
                <Button
                    type="submit"
                    disabled={isSubmitting}
                    className="bg-gray-900 text-white px-6 py-2.5 rounded-md text-sm font-medium hover:bg-gray-700 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                >
                    {isSubmitting ? "Cadastrando..." : "Adicionar"}
                </Button>
                <Button
                    type="button"
                    variant="outline"
                    onClick={onCancel}
                    className="border border-gray-300 text-gray-700 px-6 py-2.5 rounded-md text-sm font-medium hover:bg-gray-50 transition-colors"
                >
                    Cancelar
                </Button>
            </div>
        </form>
    )
}