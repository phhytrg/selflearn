import { fireEvent, render, screen } from '@testing-library/react';
import Button from './Button';
import { describe, expect, test, vi } from 'vitest';
import {userEvent} from '@testing-library/user-event';

describe('Button', () => {
  const user = userEvent.setup({

  });
  test('renders button with text', () => {
    render(<Button onClick={() => {}}>Click Me</Button>);
    const buttonElement = screen.getByText(/click me/i);
    expect(buttonElement).toBeInTheDocument();
  });

  test('calls onClick when clicked', () => {
    const handleClick = vi.fn();
    render(<Button onClick={handleClick}>Click Me</Button>);
    // const buttonElement = screen.getByText(/click me/i);
    const buttonElement = screen.getByRole('button', { name: /click me/i });
    fireEvent.click(buttonElement);
    expect(handleClick).toHaveBeenCalledTimes(1);
  });
});
