import { RouterProvider, createMemoryRouter } from 'react-router-dom';
import {
  cleanup,
  render,
  screen,
  waitFor,
} from '@testing-library/react';
import { afterAll, beforeEach, describe, expect, it, vi } from 'vitest';
import routesConfig from '@/routes';
import { AuthProvider } from '@/auth/providers/AuthProvider';
import { server } from '../msw/worker';

describe('Routes with authorized user', () => {
  vi.mock('@/auth/hooks/useAuth', (importOriginal) => {
    const mod = importOriginal<typeof import('@/auth/hooks/useAuth')>();

    return {
      useAuth: () => ({
        user: {
          sub: '123',
          email: 'test@gmail.com',
          roles: [
            {
              authority: 'ROLE_READER',
            },
          ],
          iat: 1627737315,
          exp: 1627823715,
        },
        ...mod,
      }),
    };
  });

  beforeEach(() => {
    render(
      <AuthProvider>
        <RouterProvider
          router={createMemoryRouter(routesConfig, {
            initialEntries: ['/'],
          })}
        />
      </AuthProvider>,
    );
  });

  it('should redirect from sign up page to home page when user is already logged in', async () => {
    cleanup();
    const router = createMemoryRouter(routesConfig, {
      initialEntries: ['/signup'],
    });
    render(
      <AuthProvider>
        <RouterProvider router={router} />
      </AuthProvider>,
    );

    await waitFor(() => {
      expect(screen.getByRole('link', { name: 'Home' })).toHaveAttribute(
        'href',
        '/home',
      );
    });
  });

  it('should redirect from login page to home page when user is already logged in', async () => {
    cleanup();
    const router = createMemoryRouter(routesConfig, {
      initialEntries: ['/login'],
    });
    render(
      <AuthProvider>
        <RouterProvider router={router} />
      </AuthProvider>,
    );

    await waitFor(() => {
      expect(screen.getByRole('link', { name: 'Home' })).toHaveAttribute(
        'href',
        '/home',
      );
    });
  });

  afterAll(() => {
    console.log('msw handlers: ', server.listHandlers());
  });
});
