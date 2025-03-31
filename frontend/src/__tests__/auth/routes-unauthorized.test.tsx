import { AuthProvider } from '@/auth/providers/AuthProvider';
import routesConfig from '@/routes';
import { cleanup, render, screen, waitFor } from '@testing-library/react';
import { createMemoryRouter, RouterProvider } from 'react-router-dom';
import { describe, vi, beforeEach, afterEach, it, expect, afterAll, beforeAll } from 'vitest';
import { server } from '../msw/worker';

describe('Routes with unauthorized user', () => {
  vi.mock(`@/auth/hooks/useAuth`, (importOriginal) => {
    const mod = importOriginal<typeof import('@/auth/hooks/useAuth')>();

    return {
      useAuth: () => ({
        user: null,
        ...mod,
      }),
    };
  });

  beforeEach(() => {
    const router = createMemoryRouter(routesConfig, {
      initialEntries: ['/'],
    });

    render(
      <AuthProvider>
        <RouterProvider router={router} />
      </AuthProvider>,
    );
  });

  afterEach(() => {
    // vi.restoreAllMocks();
    cleanup();
  });

  it('should first render login page for unauthorized user', async () => {
    await waitFor(() => {
      expect(
        screen.getByRole('button', {
          name: 'signIn',
        }),
      ).toBeInTheDocument();
    });
  });

  beforeAll(() => {
  });
  
  afterAll(() => {
    console.log('msw handlers: ', server.listHandlers());
  });
});
