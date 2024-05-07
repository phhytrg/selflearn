import axiosInstance from './axios-config';

export const authApi = {
  login: async (email: string, password: string) => {
    return await axiosInstance.post('/auth/login', {
      email,
      password,
    });
  },
  register: async (
    email: string,
    password: string,
    confirmPassword: string,
  ) => {
    return await axiosInstance.post('/auth/signup', {
      email,
      password,
      confirmPassword,
    });
  },
};
