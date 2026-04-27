export interface Product {
  productId: number;
  name: string;
  description: string;
  price: number;
  category?: {
    id: number;
    name: string;
  };
  collection?: {
    id: number;
    name: string;
  };
  score?: number;
}