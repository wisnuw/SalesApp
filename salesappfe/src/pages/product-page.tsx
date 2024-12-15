import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { selectUser } from "../app/authSlice";
import { axiosClient, axiosPrivate } from "../service/axios.service";
import axios, { AxiosResponse } from "axios";
import Cookies from "js-cookie";

interface Product { id: number; name: string; price: number; }

const ProductPage = () => {
    const [products, setProducts] = useState<Product[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const loggedUser = useSelector(selectUser);

    useEffect(() => {
        if (loggedUser == null) return;

        setIsLoading(true);

        const fetchProducts = async (): Promise<any> => {
            const token = Cookies.get("token");
            const headers = {
                Authorization: 'Bearer ' + token
              };

            const response = await axiosClient.get("/product", { headers })
                .then((resp: AxiosResponse) => { 
                    setProducts(resp.data);
                }) 
                .catch(error => { 
                    console.error('There was an error!', error); 
                })
                .finally(() => {
                    setIsLoading(false);
                });
        };
        
        fetchProducts();

    }, []);

    return (
        <div> 
            <h1>Product List</h1> 
            <ul> {products.map(product => ( 
                <li key={product.id}> {product.name} - Rp. {product.price} </li> )
            )} </ul> 
        </div>
    )
}

export default ProductPage