import { createContext, ReactElement, useState, useMemo } from 'react';
import { JwtResponse } from '../types/Jwt';
import { JwtPayload } from '../types/JwtPayload';
import { authApi } from '@/shared/apis/auth-api';
import { Buffer } from 'buffer';

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
  const [user, setUser] = useState<JwtPayload | null>(null);

  // call this function when you want to authenticate the user
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

  // call this function to sign out logged in user
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
