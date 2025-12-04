interface CategoryItemProps {
  image: string;
  label: string;
  href?: string;
}

export function CategoryItem({ image, label, href = "/" }: CategoryItemProps) {
  return (
    <div className="relative overflow-hidden group">
      <a href={href}>
        <img 
          className="w-[480px] h-[700px] block transition-transform duration-[400ms] ease-in-out group-hover:scale-105" 
          src={image} 
          alt={label} 
        />
      </a>
      <button className="category-button absolute bottom-8 left-1/2 -translate-x-1/2 font-['Alexandria',sans-serif] font-light text-[1.2rem] px-2 py-2 border-none bg-white cursor-pointer whitespace-nowrap opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all duration-300 ease-in-out text-black">
        {label}
      </button>
    </div>
  );
}