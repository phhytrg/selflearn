import routesConfig from '@/routes';
import {
  cleanup,
  fireEvent,
  render,
  screen,
  waitFor,
  waitForElementToBeRemoved,
} from '@testing-library/react';
import { createMemoryRouter, RouterProvider } from 'react-router-dom';
import {
  afterEach,
  beforeAll,
  beforeEach,
  describe,
  expect,
  it,
  vi,
} from 'vitest';
import { server } from '../msw/worker';
import { authHandlers } from '../msw/mocks/auth.handlers';
import { SignUpPage } from '@/auth/SignUp';
import LoginPage from '@/auth/SignIn';
import { ProtectedRoute } from '@/auth/ProtectedRoute';
import { HomePage } from '@/home/Home';

// const routesConfig = [
//   {
//     element: <SignUpPage />,
//     path: '/signup',
//   },
//   {
//     element: <LoginPage />,
//     path: '/login',
//   },
//   {
//     path: '/',
//     element: <ProtectedRoute />,
//     children: [
//       {
//         path: '/home',
//         element: <HomePage />,
//       },
//     ],
//   },
// ];

describe('SignUp Component', () => {
  server.use(...authHandlers);
  vi.mock('antd', async (importOriginal) => {
    const mod = await importOriginal<typeof import('antd')>();
    return {
      ...mod,
      Spin: () => {
        return <div aria-label="spin">Spin</div>;
      },
    };
  });

  beforeAll(() => {
  });

  beforeEach(() => {
    const router = createMemoryRouter(routesConfig, {
      initialEntries: ['/signup'],
    });
    render(<RouterProvider router={router} />);
  });

  afterEach(() => {
    cleanup();
  });

  it('should render SignUp component', () => {
    expect(screen.getByPlaceholderText('Email')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Password')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Confirm Password')).toBeInTheDocument();
    expect(screen.getByRole('button', { name: 'signUp' })).toBeInTheDocument();
  });

  it('should match the snapshot', () => {
    cleanup();
    const router = createMemoryRouter(routesConfig, {
      initialEntries: ['/signup'],
    });
    const { asFragment } = render(<RouterProvider router={router} />);
    expect(asFragment()).toMatchSnapshot();
  });

  it('should navigate to login page when user sign up successfully', async () => {
    const signUpButton = screen.getByRole('button', { name: 'signUp' });
    const emailInput = screen.getByPlaceholderText('Email');
    const passwordInput = screen.getByPlaceholderText('Password');
    const confirmPasswordInput =
      screen.getByPlaceholderText('Confirm Password');

    fireEvent.change(emailInput, { target: { value: 'test1@gmail.com' } });
    fireEvent.change(passwordInput, { target: { value: 'password' } });
    fireEvent.change(confirmPasswordInput, { target: { value: 'password' } });
    fireEvent.click(signUpButton);

    await waitForElementToBeRemoved(() => {
      const spinElement = screen.getByLabelText('spin');
      waitFor(() => expect(spinElement).not.toBeInTheDocument());

      return spinElement;
    });

    expect(screen.getByRole('button', { name: 'signIn' })).toBeInTheDocument();
  });

  it('should show error modal when password and confirm password do not match', async () => {
    const signUpButton = screen.getByRole('button', { name: 'signUp' });
    const emailInput = screen.getByPlaceholderText('Email');
    const passwordInput = screen.getByPlaceholderText('Password');
    const confirmPasswordInput =
      screen.getByPlaceholderText('Confirm Password');

    fireEvent.change(emailInput, { target: { value: 'test@gmail.com' } });
    fireEvent.change(passwordInput, { target: { value: 'password' } });
    fireEvent.change(confirmPasswordInput, { target: { value: 'password1' } });
    fireEvent.click(signUpButton);

    await waitFor(() => {
      expect(screen.getByTestId('error-modal')).toBeInTheDocument();
    });
  });
});
