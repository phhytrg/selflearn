import { render, renderHook, screen } from '@testing-library/react';
import { ThemeProvider } from './ThemeContext';
import ThemedButton from './ThemedButton';
import { describe, expect, test } from 'vitest';
import { useTheme } from './useTheme';

describe('ThemedButton', () => {
  test('renders button with theme', () => {
    render(
      <ThemeProvider theme="dark">
        <ThemedButton>Click Me</ThemedButton>
      </ThemeProvider>,
    );
    const buttonElement = screen.getByText(/click me/i);
    expect(buttonElement).toHaveClass('dark');
  });

  test('useTheme returns current theme', () => {
    const wrapper = ({ children }) => (
      <ThemeProvider theme={'dark'}>{children}</ThemeProvider>
    );

    const { result } = renderHook(() => useTheme(), { wrapper });
    expect(result.current).toBe('dark');
  });
});
