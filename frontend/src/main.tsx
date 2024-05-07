import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { SignUpPage } from './auth/SignUp';
import { LoginPage } from './auth/SignIn';
import { ProtectedRoute } from './auth/ProtectedRoute';
import { AuthProvider } from './auth/providers/AuthProvider';
import { HomePage } from './home/Home';
import { QueryClient, QueryClientProvider } from 'react-query';
import { ConfigProvider } from 'antd';
import { ProjectTab } from './project/ProjectTab';
import { TableTab } from './project/TableTab';

const queryClient = new QueryClient();

const router = createBrowserRouter([
  {
    path: '/',
    element: <ProtectedRoute />,
    children: [
      {
        path: '/home',
        element: <HomePage />,
        children: [
          {
            path: '/home/project',
            element: <ProjectTab />,
          },
          {
            path: '/home/table',
            element: <TableTab />,
          },
        ],
      },
    ],
  },
  {
    path: '/login',
    element: <LoginPage />,
  },
  {
    path: '/signup',
    element: <SignUpPage />,
  },
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <AuthProvider>
        <ConfigProvider select={{
          style: {
            width: '200px'
          }
        }}>
          <RouterProvider router={router} />
        </ConfigProvider>
      </AuthProvider>
    </QueryClientProvider>
  </React.StrictMode>,
);
