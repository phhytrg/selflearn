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
import { LandingPage } from './landing/Landing';
import { AdminPage } from './admin/Admin';
import ReactPatternsPage from './react-patterns/ReactPatternsPage';
import ClassCounter from './react-patterns/hooks-pattern/class-component';
import FunctionCounter from './react-patterns/hooks-pattern/function-component';
import HocPattern from './react-patterns/hoc-pattern';
import RenderPropsPatternPage from './react-patterns/render-props-pattern';

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
      {
        path: '/',
        element: <LandingPage />,
      },
      {
        path: '/admin',
        element: <AdminPage />,
        children: [
          {
            path: '/admin/create-node-pools',
          },
          {
            path: '/admin/delete',
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
  {
    path: '/react-patterns',
    element: <ReactPatternsPage />,
  },
  {
    path: '/react-patterns/hooks-pattern/class',
    element: <ClassCounter />,
  },
  {
    path: '/react-patterns/hooks-pattern/function',
    element: <FunctionCounter />,
  },
  {
    path: '/react-patterns/hoc-pattern',
    element: <HocPattern />,
  },
  {
    path: 'react-patterns/render-props-pattern',
    element: <RenderPropsPatternPage />,
  },
]);

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <QueryClientProvider client={queryClient}>
      <AuthProvider>
        <ConfigProvider
          select={{
            style: {
              width: '200px',
            },
          }}
        >
          <RouterProvider router={router} />
        </ConfigProvider>
      </AuthProvider>
    </QueryClientProvider>
  </React.StrictMode>,
);
