import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { selectUser } from "../app/authSlice";
import { axiosClient, axiosPrivate } from "../service/axios.service";
import { AxiosResponse } from "axios";
import Cookies from "js-cookie";

interface Product { id: number; name: string; price: number; }

const ProductPage = () => {
    const [products, setProducts] = useState<Product[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [errorMessage, setErrorMessage] = useState<string>("");
    const loggedUser = useSelector(selectUser);

    useEffect(() => {
        if (loggedUser == null) return;

        setIsLoading(true);

        const fetchProducts = async (): Promise<any> => {
            const token = Cookies.get("token");
            
            const response = await axiosPrivate
                .get("/product")
                .then((resp: AxiosResponse) => { 
                    console.log('Response:', resp); 
                    console.log('Data:', resp.data);

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
                <li key={product.id}> {product.name} - ${product.price} </li> )
            )} </ul> 
        </div>
    )
}

export default ProductPage