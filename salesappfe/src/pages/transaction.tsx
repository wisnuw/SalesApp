import { AxiosResponse } from "axios";
import Cookies from "js-cookie";
import { ChangeEvent, useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { axiosClient } from "../service/axios.service";
import { selectUser } from "../app/authSlice";
import { useNavigate } from "react-router-dom";
import { useForm } from "react-hook-form";
import Button from "../elements/button";

interface Product { 
    id: number; 
    name: string; 
    price: number; 
    stock:number; 
    amount:number;
}

interface PayloadItem {
    productId: string;
    amount: string;
}

interface Payload {
    items: PayloadItem[];
}

  
const TransactionPage = () => {
    const [products, setProducts] = useState<Product[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [errorMessage, setErrorMessage] = useState<string>("");
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

    const handleInputChange = (id: number, event: ChangeEvent<HTMLInputElement>) => {
        const newItems = products.map(item => {
            if (item.id === id) {
                return { ...item, amount: event.target.value };
            }
            return item;
        });
        setProducts(newItems);
    };
    
    const onsubmit = async (data: Product) => {
        setIsLoading(true);
        setErrorMessage("");

        const token = Cookies.get("token");
        const headers = {
            Authorization: 'Bearer ' + token
            };
        
        const payload = mapFormDataToPayload(products);
        console.log('Payload Req:', payload);

        try {
            const response = await axiosClient
            .post("/transaction/checkout", payload, { headers })
            .finally(() => {
                setIsLoading(false);
                alert(`Response received: ${response.data.status}`);
                window.location.reload();
            });
        } catch (error) {
            setErrorMessage((error as any).response.data.message);
        }
    };
    
    const {
        handleSubmit,
        formState: { errors },
    } = useForm<Product>();

    const mapFormDataToPayload = (productArray: Product[]): Payload => {
        const items = productArray.map(item => ({
            productId: item.id,
            amount: item.amount,
        }));
        
        return { items };
    };
    
    return (
        <div> 
            <h1>Transaction</h1> 
            <form
                noValidate
                onSubmit={handleSubmit(onsubmit)}
                className="bg-white/20 p-8 shadow-sm min-h-[400px] min-w-[400px]"
            >
            <ul> {products.map(product => ( <>
                <li key={product.id}> {product.name} - Rp. {product.price} - Stok {product.stock}</li>
                    Jumlah Beli <input
                        key={`textBox_${product.id}`}
                        type="text"
                        value={product.amount}
                        onChange={event => handleInputChange(product.id, event)}
                    />
                
            </>))}</ul>
            <Button isLoading={isLoading} value="Checkout!" type="submit" />
            </form>
        </div>
    )
}

export default TransactionPage