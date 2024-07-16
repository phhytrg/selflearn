import {
  afterAll,
  afterEach,
  beforeAll,
  beforeEach,
  describe,
  expect,
  it,
  MockedObject,
  vi,
} from 'vitest';
import {
  cleanup,
  fireEvent,
  render,
  screen,
  waitFor,
  waitForElementToBeRemoved,
  within,
} from '@testing-library/react';
import LoginPage from '@/auth/SignIn';
import {
  AuthContext,
  AuthContextProps,
  AuthProvider,
} from '@/auth/providers/AuthProvider';
import { JwtPayload } from '@/auth/interfaces/JwtPayload';
import {
  BrowserRouter,
  createMemoryRouter,
  RouterProvider,
} from 'react-router-dom';
import userEvent from '@testing-library/user-event';
import { handlers as authHandlers } from '../msw/mocks/auth.handlers';
import { server } from '../msw/worker';
import routesConfig from '@/routes';

// Faker Js for generating fake data
// Mws for mocking the api calls
// Vitest for testing
// React rendering library for rendering the components

// vi.mock('@/auth/hooks/useAuth', async () => {
//   return {
//     useAuth: () => {
//       return {
//         user: null,
//         login: vi.fn(
//           async ({ username }: { username: string; password: string }) => {
//             const user: JwtPayload = {
//               sub: '1',
//               roles: [{ authority: 'ROLE_READER' }],
//               email: username,
//               iat: 0,
//               exp: 0,
//             };
//             return user;
//           },
//         ),
//         logout: vi.fn(() => {
//           return null;
//         }),
//       };
//     },
//   };
// });

server.use(...authHandlers);

vi.mock('antd', async (importOriginal) => {
  const mod = await importOriginal<typeof import('antd')>();
  return {
    ...mod,
    Spin: () => <div aria-label="spin">Loading...</div>,
  };
});

const customRender = (ui: React.ReactNode, { providerProps }) => {
  return render(
    <AuthContext.Provider value={providerProps}>{ui}</AuthContext.Provider>,
    {
      wrapper: BrowserRouter,
    },
  );
};

describe('SignIn Component', () => {
  let providerProps: MockedObject<AuthContextProps>;

  beforeAll(() => {
    // What to do before all tests
    // JSDom does not implement this and an error was being
    // thrown from jest-axe because of it.

    window.getComputedStyle = (elt: Element) => {
      return {
        getPropertyValue: (prop: string) => {
          return '';
        },
      } as CSSStyleDeclaration;
    };
  });

  beforeEach(() => {
    // Thing to do before each tests
    providerProps = {
      user: null,
      login: vi.fn(
        async ({ username }: { username: string; password: string }) => {
          const user: JwtPayload = {
            sub: '1',
            roles: [{ authority: 'ROLE_READER' }],
            email: username,
            iat: 0,
            exp: 0,
          };
          providerProps.user = user;
        },
      ),
      logout: vi.fn(() => {
        providerProps.user = null;
      }),
    };
    customRender(<LoginPage />, { providerProps });
  });

  afterEach(() => {
    // Thing to do after each tests
    cleanup();
  });

  it('should match the snapshot', () => {
    server.use(...authHandlers);
    console.log(server.listHandlers());
    cleanup();
    const { asFragment } = customRender(<LoginPage />, { providerProps });
    expect(asFragment()).toMatchSnapshot();
  });

  it('should render the SignIn component', () => {
    // console.log(server.listHandlers());
    // Sign in component should have email and password fields and a login button, a sign up button
    expect(screen.getByLabelText('Email:')).toBeInTheDocument();
    expect(screen.getByLabelText('Password:')).toBeInTheDocument();
    expect(screen.getByRole('textbox', { name: 'email' })).toBeInTheDocument();
    expect(screen.getByLabelText('password')).toBeInTheDocument();
    expect(screen.getByRole('button', { name: 'signIn' })).toBeInTheDocument();
    expect(screen.getByRole('button', { name: 'signUp' })).toBeInTheDocument();
  });

  it('should block sign in button when request is in progress', async () => {
    const buttonElement = screen.getByRole('button', { name: 'signIn' });
    fireEvent.click(buttonElement);
    expect(buttonElement).toBeDisabled();

    await waitForElementToBeRemoved(() => {
      const spinElement = screen.getByLabelText('spin');
      waitFor(() => expect(spinElement).not.toBeInTheDocument());

      return spinElement;
    });
    expect(buttonElement).toBeEnabled();
  });

  const handleLogin = vi.fn();
  it('should call the login function when the form is submitted', async () => {
    const buttonElement = screen.getByRole('button', { name: 'signIn' });
    buttonElement.onclick = handleLogin;
    fireEvent.click(buttonElement);

    expect(handleLogin).toHaveBeenCalled();
  });

  it('should show an error message when the email is invalid', async () => {
    const emailInput = screen.getByRole('textbox', { name: 'email' });
    userEvent.type(emailInput, 'invalid-email');
    const buttonElement = screen.getByRole('button', { name: 'signIn' });
    const passwordElem = screen.getByLabelText('password');
    const emailElem = screen.getByLabelText('email');

    fireEvent.change(emailElem, { target: { value: 'test@gmail.com' } });
    fireEvent.change(passwordElem, { target: { value: 'Test@1234' } });
    fireEvent.click(buttonElement);

    await waitForElementToBeRemoved(() => {
      const spinElement = screen.getByLabelText('spin');
      waitFor(() => expect(spinElement).not.toBeInTheDocument());

      return spinElement;
    });

    // Check if the error message is displayed
  });

  it('should go to sign up page when sign up button is clicked', async () => {
    cleanup();
    const router = createMemoryRouter(routesConfig, {
      initialEntries: ['/home'],
    });
    render(<RouterProvider router={router} />);

    const signUpButton = screen.getByRole('button', { name: 'signUp' });
    fireEvent.click(signUpButton);

    await waitFor(() => {
      expect(
        screen.getByRole('heading', { name: 'Sign Up' }),
      ).toBeInTheDocument();
    });
  });

  it('should show an error message when the password is invalid', async () => {
    cleanup();
    vi.resetModules();
    const router = createMemoryRouter(routesConfig, {
      initialEntries: ['/login'],
    });

    render(
      <AuthProvider>
        <RouterProvider router={router} />
      </AuthProvider>,
    );
    const passwordElem = screen.getByLabelText('password');
    const emailElem = screen.getByLabelText('email');
    const buttonElement = screen.getByRole('button', { name: 'signIn' });

    fireEvent.change(emailElem, { target: { value: 'test@gmail.com' } });
    // Valid password should be Abc@1234
    fireEvent.change(passwordElem, { target: { value: 'abc@1234' } });
    fireEvent.click(buttonElement);

    await waitForElementToBeRemoved(() => {
      const spinElement = screen.getByLabelText('spin');
      waitFor(() => expect(spinElement).not.toBeInTheDocument());

      return spinElement;
    });

    const dialog = await screen.findByRole('dialog');

    expect(dialog).toBeInTheDocument();
    const errorMessage = await within(dialog).findByText(/Error/i);
    expect(errorMessage).toBeInTheDocument();

    // expect(screen.getByTestId('error-modal')).toBeInTheDocument();
  });

  it('should navigate to home page when user is logged in', async () => {
    cleanup();
    const router = createMemoryRouter(routesConfig, {
      initialEntries: ['/home'],
    });

    render(
      <AuthProvider>
        <RouterProvider router={router} />
      </AuthProvider>,
    );
    
    const buttonElement = screen.getByRole('button', { name: 'signIn' });
    const passwordElem = screen.getByLabelText('password');
    const emailElem = screen.getByLabelText('email');

    fireEvent.change(emailElem, { target: { value: 'test@gmail.com' } });
    fireEvent.change(passwordElem, { target: { value: 'Test@1234' } });
    fireEvent.click(buttonElement);

    await waitForElementToBeRemoved(() => {
      const spinElement = screen.getByLabelText('spin');
      waitFor(() => expect(spinElement).not.toBeInTheDocument());

      return spinElement;
    });

    // expect(await screen.findByTestId('error-modal'))?.not.toBeInTheDocument();
    // expect(await screen.findByRole('dialog', {name: /Error/i})).toBeInTheDocument();

    // Check if the user is redirected to the home page
    expect(screen.getByRole('link', { name: 'Home' })).toHaveAttribute(
      'href',
      '/home',
    );
  });

  afterAll(() => {
    console.log('msw handlers: ', server.listHandlers());
    // server.resetHandlers();
  });
});
