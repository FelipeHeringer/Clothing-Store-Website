import { CategoryItem } from "./CategoryItem";


interface CategoryItemData {
    image: string;
    label: string;
    href?: string;
}

interface CategoryProps {
    categories: CategoryItemData[];
    className?: string;
}

export function Category({ categories, className = "" }: CategoryProps) {
    return (
        <>
            <style>{`
        @import url('https://fonts.googleapis.com/css2?family=Alexandria:wght@400;500;700&display=swap');
        
        button::before {
          content: "";
          position: absolute;
          width: 0;
          height: 2px;
          bottom: 0.2rem;
          left: 50%; 
          transform: translateX(-50%);
          background-color: black;
          transition: width 0.3s ease-in-out;
        }

        .category-button:hover::before {
          width: 80%;
        }
      `}</style>

            <div className={`whitespace-nowrap w-full flex gap-0 justify-center mt-0 mb-0 ${className}`}>
                {categories.map((category, index) => (
                    <CategoryItem
                        key={index}
                        image={category.image}
                        label={category.label}
                        href={category.href}
                    />
                ))}
            </div>
        </>
    );
}