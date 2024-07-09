import { ConfigProvider } from 'antd';
import { QueryClientProvider } from 'react-query';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { queryClient } from './main';
import { AuthProvider } from './auth/providers/AuthProvider';
import routesConfig from './routes';

const router = createBrowserRouter(routesConfig);

const App = () => {
  return (
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
  );
};

export default App;
