import { createContext, ReactElement, useMemo } from 'react';
import { JwtResponse } from '../interfaces/Jwt';
import { JwtPayload } from '../interfaces/JwtPayload';
import { Buffer } from 'buffer';
import { useLocalStorage } from '@/shared/hooks/useLocalStorage';
import { authApi } from '@/shared/apis';

export const AuthContext = createContext<{
  user: JwtPayload | null;
  login: ({
    username,
    password,
  }: {
    username: string;
    password: string;
  }) => Promise<void>;
  logout: () => void;
}>({
  user: null,
  login: async () => {}, // Fix: Change the return type to Promise<void>
  logout: () => {},
});

export const AuthProvider = ({ children }: { children: ReactElement }) => {
  const [user, setUser] = useLocalStorage('user', null);

  const login = async ({
    username,
    password,
  }: {
    username: string;
    password: string;
  }) => {
    const res: JwtResponse = (await authApi.login(username, password)).data;
    const jwtPayload: JwtPayload = JSON.parse(
      Buffer.from(res.accessToken.split('.')[1], 'base64').toString(),
    );
    setUser(jwtPayload);
    localStorage.setItem('accessToken', res.accessToken);
    localStorage.setItem('refreshToken', res.refreshToken);
  
    return Promise.resolve();
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
  };

  const value = useMemo(
    () => ({
      user,
      login,
      logout, // Fix: Change the return type to () => null
    }),
    [user],
  );

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};
