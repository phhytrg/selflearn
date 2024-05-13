import axiosInstance from './axios-config';

export const resourceGroupApi = {
  getBySubscriptionName: async (subscriptionName?: string) => {
    return await axiosInstance.get('/resource-groups', {
      params: {
        subscriptionName,
      },
    });
  },
};
