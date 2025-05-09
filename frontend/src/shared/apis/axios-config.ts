import axios, { AxiosError, AxiosInstance } from 'axios';
import { HOST_API } from '../constants';
import { NoConnectionError } from '../errors/no-connection';

axios.defaults.baseURL = HOST_API;

const axiosInstance: AxiosInstance = axios.create({
  baseURL: `${HOST_API}/api/v1`,
  headers: {
    'Content-type': 'application/json',
  },
});

axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('accessToken');
    config.headers.Authorization = token ? `Bearer ${token}` : undefined;
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

axiosInstance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response?.status === 401) {
      const originalRequest = error.config;
      const refreshToken = localStorage.getItem('refreshToken');
      if (!refreshToken) {
        return Promise.reject(error);
      }
      return axios
        .post(`${HOST_API}/api/v1/auth/refresh`, {
          refreshToken: refreshToken,
        })
        .then((response) => {
          localStorage.setItem('accessToken', response.data.accessToken);
          error.config.headers['Authorization'] =
            `Bearer ${response.data.accessToken}`;
          return axiosInstance(originalRequest);
        })
        .catch((e) => {
          localStorage.removeItem('accessToken');
          localStorage.removeItem('refreshToken');
          localStorage.removeItem('user');
        });
    }
    if (error.code === AxiosError.ERR_NETWORK) {
      throw new NoConnectionError();
    }
    return Promise.reject(error);
  },
);

export default axiosInstance;
