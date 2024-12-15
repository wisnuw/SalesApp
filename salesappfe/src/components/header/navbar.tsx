import { useEffect } from "react";
import { useSelector } from "react-redux/es/exports";
import { selectUser, setUser } from "../../app/authSlice";
import jwt from "jwt-decode";
import Cookies from "js-cookie";
import { useDispatch } from "react-redux/es/hooks/useDispatch";
import { Link, useNavigate } from "react-router-dom";
import Button from "../elements/button";
import { axiosPrivate } from "../../service/axios.service";
import useRefreshToken from "../../hooks/use-refresh-token";
import { AxiosResponse } from "axios";

const Navbar = () => {
  const loggedUser = useSelector(selectUser);
  const dispatch = useDispatch();
  const refresh = (loggedUser) ? useRefreshToken() : null;
  const navigate = useNavigate();
  
  const decodeAndStore = (token: string) => {
    const payload: { username: string } = jwt(token);
    dispatch(setUser({ username: payload.username }));
  };

  useEffect(() => {
    if (loggedUser) return;

    const token = Cookies.get("token");

    if (token) {
      decodeAndStore(token);
    } else {
      // refresh().then((token) => decodeAndStore(token));
    }
  }, []);

  const notLoggedIn = (
    <>
      <Link to="/auth/login">
        <Button value="Get Started" className="w-max px-6 text-xl" />
      </Link>
    </>
  );

  const logout = async (): Promise<void> => {
    const token = Cookies.get("token");
    const headers = {
        Authorization: 'Bearer ' + token
    };
    
    const response = await axiosPrivate
      .post("/auth/logout", { headers })
      .then((resp: AxiosResponse) => { 
        console.log(resp.data);
        Cookies.remove("token");
        navigate("/");
      }) 
      .catch(error => { 
        console.error('There was an error!', error); 
      })
      .finally(() => {
        
      });
  };

  return (
    <nav className="w-full bg-blue-600/10 font-bold text-slate-900 text-center p-4">
      <div className="flex space-y-3 lg:space-y-0 lg:space-x-6 flex-col lg:flex-row items-center justify-center">
        {loggedUser ? (
          <>
            <Link to="/transaction">
              <Button value={"Transaction"} className="w-max px-6 text-xl" />
            </Link>
            <Link to="/report">
              <Button value={"Report"} className="w-max px-6 text-xl" />
            </Link>
            <Link to="/product">
              <Button value={"Product"} className="w-max px-6 text-xl" />
            </Link>
            <Link to="/">
              <Button
                value={`Logout`}
                onClick={logout}
                className="w-max bg-red-400 hover:bg-red-500 px-6 text-xl" />
            </Link>
          </>
        ) : (
          notLoggedIn
        )}
      </div>
    </nav>
  );
};

export default Navbar;
