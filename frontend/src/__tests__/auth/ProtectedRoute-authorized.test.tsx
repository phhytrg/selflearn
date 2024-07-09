import { ProtectedRoute } from '@/auth/ProtectedRoute';
import routesConfig from '@/routes';
import {
  fireEvent,
  within,
  waitFor,
  screen,
  render,
} from '@testing-library/react';
import { createMemoryRouter, RouterProvider } from 'react-router-dom';
import { beforeEach, describe, expect, it, vi } from 'vitest';

describe('ProtectedRoute Component with authorized user', () => {
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
      <RouterProvider
        router={createMemoryRouter(routesConfig, {
          initialEntries: ['/'],
        })}
      />,
    );
  });

  it('should close drawer when user clicks on close button', async () => {
    const menuButton = screen.getByRole('button', { name: 'menu' });
    fireEvent.click(menuButton);

    const drawerElem = await screen.findByTestId('drawer');
    const closeButtonElem = within(drawerElem).getByRole('button', {
      name: 'Close',
    });
    expect(closeButtonElem).toBeInTheDocument();
    fireEvent.click(closeButtonElem);

    await waitFor(() => {
      expect(drawerElem).not.toHaveClass('ant-drawer-open');
    });
  });

  it('should open drawer when user clicks on the menu button', async () => {
    const menuButton = screen.getByRole('button', { name: 'menu' });
    expect(menuButton).toBeInTheDocument();
    fireEvent.click(menuButton);

    const drawerElem = await screen.findByTestId('drawer');
    expect(drawerElem).toBeInTheDocument();
  });
});
