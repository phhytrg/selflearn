import { createContext } from 'react';

export const ThemeContext = createContext('light');

export const ThemeProvider = ({ children, theme }) => (
  <ThemeContext.Provider value={theme}>{children}</ThemeContext.Provider>
);
