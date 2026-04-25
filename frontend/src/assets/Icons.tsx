interface IconProps {
    color?: string;
    width?: number;
    height?: number;
    className?: string;
}

export function CloseIcon({ color = "#343434", width = 24, height = 24, className = "" }: IconProps) {
    return (
        <svg
            xmlns="http://www.w3.org/2000/svg"
            width={width}
            height={height}
            fill={color}
            viewBox="0 -960 960 960"
            className={className}
        >
            <path d="m256-200-56-56 224-224-224-224 56-56 224 224 224-224 56 56-224 224 224 224-56 56-224-224z"></path>
        </svg>
    );
}

export function PlusIcon({ color = "#343434", width = 24, height = 24, className = "" }: IconProps) {
    return (
        <svg
            xmlns="http://www.w3.org/2000/svg"
            width={width}
            height={height}
            fill={color}
            viewBox="0 -960 960 960"
            className={className}
        >
            <path d="M440-440H200v-80h240v-240h80v240h240v80H520v240h-80z"></path>
        </svg>
    );
}

export function FavoriteIcon({ color = "#343434", width = 24, height = 24, className = "" }: IconProps) {
    return (
        <svg
            xmlns="http://www.w3.org/2000/svg"
            width={width}
            height={height}
            fill="none"
            className={className}
            viewBox="0 0 24 24"
        >
            <path
                stroke={color}
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="1.5"
                d="M11.245 4.174c.232-.666.347-.999.518-1.091a.5.5 0 0 1 .475 0c.171.092.287.425.518 1.091l1.53 4.402c.066.19.1.285.159.355a.5.5 0 0 0 .195.142c.085.034.185.036.386.04l4.66.096c.705.014 1.057.021 1.198.155a.5.5 0 0 1 .146.452c-.035.191-.315.404-.877.83l-3.714 2.816c-.16.12-.24.181-.289.26a.5.5 0 0 0-.074.229c-.007.092.022.188.08.38l1.35 4.46c.204.676.306 1.013.222 1.188a.5.5 0 0 1-.384.28c-.193.025-.482-.176-1.06-.579l-3.826-2.662c-.165-.114-.247-.172-.337-.194a.5.5 0 0 0-.24 0c-.09.022-.173.08-.337.194L7.718 19.68c-.579.403-.868.604-1.06.578a.5.5 0 0 1-.385-.279c-.084-.175.018-.512.222-1.187l1.35-4.461c.058-.192.087-.288.08-.38a.5.5 0 0 0-.074-.23c-.049-.078-.128-.138-.288-.26l-3.714-2.815c-.562-.426-.843-.639-.878-.83a.5.5 0 0 1 .147-.452c.14-.134.493-.141 1.198-.155l4.66-.095c.2-.005.3-.007.386-.041a.5.5 0 0 0 .195-.142c.059-.07.092-.165.158-.355z"
            ></path>
        </svg>
    )
}
