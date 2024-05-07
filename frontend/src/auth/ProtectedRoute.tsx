import { Navigate, Outlet } from 'react-router-dom';
import { useAuth } from './hooks/useAuth';

export const ProtectedRoute = () => {
  const { user } = useAuth();
  if (!user) {
    //Try to get user from local storage
    return <Navigate to="/login" />;
  }
  return <Outlet />;
};
