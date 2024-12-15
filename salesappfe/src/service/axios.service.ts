import axios from "axios";

const BACKEND_URL = import.meta.env.VITE_BACKEND_URL; //? This is the server's base url

export const axiosClient = axios.create({
  baseURL: BACKEND_URL ?? "http://localhost:8080/api",
});

export const axiosPrivate = axios.create({
  baseURL: BACKEND_URL ?? "http://localhost:8080/api",
  withCredentials: true
});
