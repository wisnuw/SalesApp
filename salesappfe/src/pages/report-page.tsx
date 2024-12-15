import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { selectUser } from "../app/authSlice";
import Cookies from "js-cookie";
import { AxiosResponse } from "axios";
import { axiosClient } from "../service/axios.service";

interface Transactions { id: string; items: [TransactionDetail]; grandTotalPrice: number; createdAt:Date}
interface TransactionDetail { name: string; amount: number; totalPrice: number; }

const ReportPage = () => {
    const [transactions, setTransactions] = useState<Transactions[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const loggedUser = useSelector(selectUser);

    useEffect(() => {
        if (loggedUser == null) return;

        setIsLoading(true);

        const fetchReport = async (): Promise<any> => {
            const token = Cookies.get("token");
            const headers = {
                Authorization: 'Bearer ' + token
                };

            const response = await axiosClient.get("/transaction", { headers })
                .then((resp: AxiosResponse) => { 
                    console.log('Response:', resp); 
                    console.log('Data:', resp.data);

                    setTransactions(resp.data);
                }) 
                .catch(error => { 
                    console.error('There was an error!', error); 
                })
                .finally(() => {
                    setIsLoading(false);
                });
        };
        
        fetchReport();

    }, []);

    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };

    return (
        <div> 
            <h1>Report</h1> 
            <ul> {transactions.map(txn => ( 
                <li key={txn.id}> {txn.createdAt.toString()} - ${txn.grandTotalPrice} </li> )
            )} </ul> 
        </div>
    )
}

export default ReportPage