import { ReactNode } from "react";
import Navbar from "../header/navbar";

interface IMainLayoutProps {
  children: ReactNode;
}

const MainLayout = ({ children }: IMainLayoutProps) => {
  return (
    <>
      <header>
        <Navbar />
      </header>
      <main className="app">{children}</main>
      <footer className="p-4 bg-blue-300/50">
        <div className="text-2xl text-center font-bold text-slate-800">
          SalesApp - Aplikasi Penjualan Sederhana
        </div>
      </footer>
    </>
  );
};

export default MainLayout;
