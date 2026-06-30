"use client";

import { Heart, Menu, Search, ShoppingCart, User, X } from "lucide-react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";
import { Button } from "@/shared/components/ui/button";

export function NavBar() {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const router = useRouter();

    const navLinks = [
        { label: 'MAIS VENDIDOS', href: '#' },
        { label: 'CAMISETAS', href: '#' },
        { label: 'LANÇAMENTOS', href: '#' },
        { label: 'COLEÇÕES', href: '#' },
    ];

    return (
        <header className="fixed top-0 left-0 w-full z-50 bg-white/95 backdrop-blur-md border-b border-gray-100 transition-all duration-300">
            <nav className="w-full h-10 md:h-10 px-[4%] flex items-center justify-between">
                {/* Mobile Menu Button */}
                <button
                    className="md:hidden text-black p-2 -ml-2"
                    onClick={() => setIsMenuOpen(true)}
                    aria-label="Open menu"
                >
                    <Menu className="w-6 h-6" strokeWidth={1.25} />
                </button>

                {/* Logo */}
                <div className="absolute left-1/2 -translate-x-1/2 font-archivo-black text-[1.2rem] md:text-title tracking-tight text-black">
                    <Link href="/">vérticecompany</Link>
                </div>

                {/* Desktop Navigation Links */}
                <ul className="hidden md:flex h-full items-center gap-8 font-inter text-[0.9rem] font-medium tracking-wide">
                    {navLinks.map((link) => (
                        <li key={link.label} className="h-full flex items-center">
                            <a
                                href={link.href}
                                className="relative h-full flex items-center text-black transition-colors group"
                            >
                                {link.label}
                                {/* Underline on hover */}
                                <span className="absolute bottom-0 left-0 w-full h-[2px] bg-black scale-x-0 group-hover:scale-x-100 transition-transform origin-left duration-300"></span>
                            </a>
                        </li>
                    ))}
                </ul>

                {/* Icons */}
                <div className="flex items-center gap-2 md:gap-4 ml-auto">
                    <Button variant="ghost" size="icon" className="hover:bg-gray-100/50" aria-label="Search" asChild>
                        <Link href="/search">
                            <Search className="size-[1.4rem] md:size-[1.6rem] text-black" strokeWidth={1.25} />
                        </Link>
                    </Button>
                    <Button variant="ghost" size="icon" className="hover:bg-gray-100/50" aria-label="Favorites" asChild>
                        <Link href="/favorites">
                            <Heart className="size-[1.4rem] md:size-[1.6rem] text-black" strokeWidth={1.25} />
                        </Link>
                    </Button>
                    <Button variant="ghost" size="icon" className="hover:bg-gray-100/50" aria-label="Shopping Cart" asChild>
                        <Link href="/cart">
                            <ShoppingCart className="size-[1.4rem] md:size-[1.6rem] text-black" strokeWidth={1.25} />
                        </Link>
                    </Button>
                    <Button variant="ghost" size="icon" className="hover:bg-gray-100/50 hidden md:flex" aria-label="User Account" onClick={() => router.push('/account')}>
                        <User className="size-[1.6rem] text-black" strokeWidth={1.25} />
                    </Button>
                </div>
            </nav>

            {/* Mobile Menu Overlay */}
            <div
                className={`fixed inset-0 bg-white z-[60] flex flex-col transition-transform duration-500 ease-in-out ${isMenuOpen ? 'translate-x-0' : 'translate-x-full'}`}
            >
                <div className="flex justify-between items-center p-4 border-b border-gray-100">
                    <span className="font-archivo-black text-xl">menu.</span>
                    <button
                        className="p-2"
                        onClick={() => setIsMenuOpen(false)}
                        aria-label="Close menu"
                    >
                        <X className="w-6 h-6" strokeWidth={1.25} />
                    </button>
                </div>

                <ul className="flex flex-col p-6 gap-6 font-inter text-2xl font-semibold mt-8">
                    {navLinks.map((link) => (
                        <li key={link.label}>
                            <a
                                href={link.href}
                                onClick={() => setIsMenuOpen(false)}
                                className="block text-black hover:text-gray-500 transition-colors"
                            >
                                {link.label}
                            </a>
                        </li>
                    ))}
                    <li className="mt-8 pt-8 border-t border-gray-100">
                        <a href="/account" onClick={() => setIsMenuOpen(false)} className="flex items-center gap-3 text-lg font-medium text-gray-600">
                            <User className="w-5 h-5" strokeWidth={1.25} /> Minha Conta
                        </a>
                    </li>
                </ul>
            </div>
        </header>
    );
}
