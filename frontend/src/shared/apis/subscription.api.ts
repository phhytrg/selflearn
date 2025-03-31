import axiosInstance from './axios-config';

export const subscriptionApi = {
  getAll: async () => {
    return await axiosInstance.get('/subscriptions');
  },
};
