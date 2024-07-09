import { useTheme } from './useTheme';

const ThemedButton = ({ children }) => {
  const theme = useTheme();
  return <button className={theme}>{children}</button>;
};

export default ThemedButton;
