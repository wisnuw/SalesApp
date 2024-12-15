import { Route, Routes, Navigate } from "react-router-dom";
import { Provider } from "react-redux";
import HomePage from './pages/home-page';
import LoginPage from './pages/auth/login-page';
import { store } from "./app/store";
import RegisterPage from './pages/auth/register-page';
import TransactionPage from './pages/transaction';
import MainLayout from "./components/layouts/main-layout";
import ReportPage from "./pages/report-page";
import ProductPage from "./pages/product-page";

function App() {
  return (
    <Provider store={store}>
      <MainLayout>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="auth">
            <Route path="login" element={<LoginPage />} />
            <Route path="register" element={<RegisterPage />} />
          </Route>
          <Route path="transaction" element={<TransactionPage />} />
          <Route path="report" element={<ReportPage />} />
          <Route path="product" element={<ProductPage />} />
          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </MainLayout>
    </Provider>
  )
}

export default App
