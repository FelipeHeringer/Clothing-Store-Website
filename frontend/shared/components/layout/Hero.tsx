export function Hero() {
    return (
        <>
            <style>{`
                button {
                    transition: all 0.3s ease-in-out;
                }
                button:hover {
                    background-color: white;
                    color: black;
                    border-color: white;
                `}
            </style>
            <div className="relative bg-[url('/assets/hero-img.png')] bg-no-repeat bg-center w-full h-[80vh]">
                <div className="absolute top-[70%] left-[3%] text-white font-['Archivo_Black',sans-serif]">
                    <h1 className="text-[5.6rem] mb-0 uppercase font-bold">Liberte-se.</h1>
                    <h2 className="text-[2.4rem] font-bold">Descubra mais sobre nossa primeira coleção</h2>
                </div>
                <button className="absolute top-[80%] right-32 text-[3vh] p-[1.5vh] font-['Alexandria']  border-solid border-white border-2 bg-transparent cursor-pointer w-[25vh]">COMPRE AGORA</button>
            </div>
        </>
    )
}
