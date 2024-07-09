import { AdminPage } from './admin/Admin';
import { ProtectedRoute } from './auth/ProtectedRoute';
import LoginPage from './auth/SignIn';
import { SignUpPage } from './auth/SignUp';
import { HomePage } from './home/Home';
import { LandingPage } from './landing/Landing';
import { ProjectTab } from './project/ProjectTab';
import { TableTab } from './project/TableTab';
import HocPattern from './react-patterns/hoc-pattern';
import ClassCounter from './react-patterns/hooks-pattern/class-component';
import FunctionCounter from './react-patterns/hooks-pattern/function-component';
import ReactPatternsPage from './react-patterns/ReactPatternsPage';
import RenderPropsPatternPage from './react-patterns/render-props-pattern';

const routesConfig = [
  {
    path: '*',
    element: (
      <div>
        <h1>404 Not Found</h1>
        <p>The page you are looking for does not exist</p>
      </div>
    ),
  },
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
];

export default routesConfig;
