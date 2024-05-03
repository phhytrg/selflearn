import { createContext, ReactElement, useState, useMemo } from 'react';
import { JwtResponse } from '../interfaces/Jwt';
import { JwtPayload } from '../interfaces/JwtPayload';
import { authApi } from '@/shared/apis/auth-api';
import { Buffer } from 'buffer';
import { useLocalStorage } from '@/shared/hooks/useLocalStorage';

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
    const JwtPayload: JwtPayload = JSON.parse(
      Buffer.from(res.accessToken.split('.')[1], 'base64').toString(),
    );
    setUser(JwtPayload);
    return Promise.resolve();
  };

  const logout = () => {
    setUser(null);
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
