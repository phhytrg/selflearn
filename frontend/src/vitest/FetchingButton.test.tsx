import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import { beforeEach, describe, expect, it, MockedFunction, vi } from 'vitest';
import FetchButton from './FetchingButton';
import axios from 'axios';

describe('FetchButton', () => {
  beforeEach(() => {
  });

  it('fetches and displays data when button is clicked', async () => {
    const mockFetchData = vi.fn().mockResolvedValue({ data: 'Hello World' });
    render(<FetchButton fetchData={mockFetchData} />);
    const buttonElement = screen.getByText(/fetch data/i);
    fireEvent.click(buttonElement);
    // const dataElement = await waitFor(() => screen.getByText(/hello world/i));
    const dataElement = await screen.findByText(/hello world/i);
    expect(dataElement).toBeInTheDocument();
  });

  vi.mock('axios', () => {
    return {
      default: {
        post: vi.fn(),
        get: vi.fn(),
        delete: vi.fn(),
        put: vi.fn(),
        create: vi.fn().mockReturnThis(),
        interceptors: {
          request: {
            use: vi.fn(),
            eject: vi.fn(),
          },
          response: {
            use: vi.fn(),
            eject: vi.fn(),
          },
        },
      },
    };
  });

  it('fetches and displays data from API', async () => {
    (axios.get as MockedFunction<typeof axios.get>).mockResolvedValue({
      data: 'Hello World',
    });
    render(<FetchButton fetchData={axios.get} />);
    const buttonElement = screen.getByText(/fetch data/i);
    fireEvent.click(buttonElement);
    const dataElement = await waitFor(() => {
      return screen.getByText(/hello world/i);
    });
    expect(dataElement).toBeInTheDocument();
  });
});
