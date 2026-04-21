import { Heart, Menu, Search, ShoppingCart, User, X } from "lucide-react";
import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { Button } from "./ui/button";

export function NavBar() {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const navigate = useNavigate();

    const navLinks = [
        { label: 'MAIS VENDIDOS', href: '#' },
        { label: 'CAMISETAS', href: '#' },
        { label: 'LANÇAMENTOS', href: '#' },
        { label: 'COLEÇÕES', href: '#' },
    ];
    return (
        <>
            <style>{`
        @import url('https://fonts.googleapis.com/css2?family=Alexandria:wght@400;500;700&display=swap');
        
        .nav-link::before {
          content: "";
          position: absolute;
          width: 0%;
          height: 3px;
          bottom: -5px;
          left: 0;
          background-color: transparent;
          transition: all 0.5s ease-in-out;
        }

        .nav-link:hover::before {
          width: 100%;
          background-color: black;
        }

        header:hover nav {
          background-color: white;
        }

        header:hover .logo,
        header:hover .nav-link {
          color: black;
        }

        header:hover .nav-icon {
          color: black;
        }

        header:hover .mobile-menu-btn {
          color: black;
        }
      `}</style>
            <header className="font-['Britanica',sans-serif]">
                <nav className="w-full fixed top-0 left-0 right-0 px-[3%] py-4 bg-transparent border-b border-white/5 backdrop-blur-[15px] flex items-center justify-between transition-colors duration-300 ease-in-out z-10">
                    {/* Mobile Menu Button */}
                    <button
                        className="mobile-menu-btn md:hidden text-white transition-colors duration-300 ease-in-out"
                        onClick={() => setIsMenuOpen(true)}
                        aria-label="Open menu"
                    >
                        <Menu className="w-6 h-6" />
                    </button>

                    {/* Logo - Centered */}
                    <div className="logo absolute left-1/2 -translate-x-1/2 text-[1.8rem] md:text-[1.8rem] text-white font-bold transition-colors duration-300 ease-in-out max-[480px]:text-[1.2rem]">
                        vérticecompany
                    </div>

                    {/* Desktop Navigation Links */}
                    <ul className="hidden md:flex list-none gap-8 font-['Alexandria',sans-serif]">
                        {navLinks.map((link) => (
                            <li key={link.label}>
                                <a
                                    href={link.href}
                                    className="nav-link relative text-[1.05rem] font-medium text-white no-underline transition-colors duration-300 ease-in-out"
                                >
                                    {link.label}
                                </a>
                            </li>
                        ))}
                    </ul>

                    {/* Icons */}
                    <ul className="flex list-none gap-4 md:gap-4 max-[768px]:gap-[0.7rem]">
                        <li>
                            <Button variant="ghost" aria-label="Search" asChild>
                                <Link to="/search">
                                    <Search className="nav-icon w-[1.6rem] h-[1.6rem] max-[768px]:w-[1.4rem] max-[768px]:h-[1.4rem] text-white transition-colors duration-300 ease-in-out" />
                                </Link>
                            </Button>
                        </li>
                        <li>
                            <Button variant="ghost" aria-label="Favorites" asChild>
                                <Link to="/favorites">
                                    <Heart className="nav-icon w-[1.6rem] h-[1.6rem] max-[768px]:w-[1.4rem] max-[768px]:h-[1.4rem] text-white transition-colors duration-300 ease-in-out" />
                                </Link>
                            </Button>
                        </li>
                        <li>
                            <Button variant="ghost" aria-label="Shopping Cart" asChild>
                                <Link to="/cart">
                                    <ShoppingCart className="nav-icon w-[1.6rem] h-[1.6rem] max-[768px]:w-[1.4rem] max-[768px]:h-[1.4rem] text-white transition-colors duration-300 ease-in-out" />
                                </Link>
                            </Button>
                        </li>
                        <li>
                            <Button variant="ghost" aria-label="User Account" onClick={() => navigate('/account')}>
                                <User className="nav-icon w-[1.6rem] h-[1.6rem] max-[768px]:w-[1.4rem] max-[768px]:h-[1.4rem] text-white transition-colors duration-300 ease-in-out" />
                            </Button>
                        </li>
                    </ul>
                </nav>

                {/* Mobile Menu */}
                <div className={`fixed top-0 left-0 w-full h-screen bg-white z-20 flex flex-col items-center justify-center transition-transform duration-300 ease-in-out ${isMenuOpen ? 'translate-x-0' : 'translate-x-full'}`}>
                    <button
                        className="absolute top-8 right-[3%] text-black"
                        onClick={() => setIsMenuOpen(false)}
                        aria-label="Close menu"
                    >
                        <X className="w-7 h-7" />
                    </button>

                    <ul className="flex flex-col items-center list-none gap-8 font-['Alexandria',sans-serif]">
                        {navLinks.map((link) => (
                            <li key={link.label}>
                                <a
                                    href={link.href}
                                    onClick={() => setIsMenuOpen(false)}
                                    className="text-2xl font-medium text-black no-underline hover:opacity-70 transition-opacity"
                                >
                                    {link.label}
                                </a>
                            </li>
                        ))}
                    </ul>
                </div>
            </header >
        </>
    )
}