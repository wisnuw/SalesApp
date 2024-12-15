import { useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { selectUser } from "../app/authSlice";
import TransactionPage from "./transaction";
import Button from "../elements/button";

const HomePage = () => {
    const loggedUser = useSelector(selectUser);
  
    return (
        <div>
            {loggedUser ? (
                <TransactionPage />
            ) : (
                <Link to="/auth/login">
                    <Button value={"Go Login!"} />
                </Link>
            )}
        </div>
    )
}

export default HomePage