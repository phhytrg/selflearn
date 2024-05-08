import axiosInstance from './axios-config';

export const clusterApi = {
  getClusters: async (params: {
    subscriptionName?: string;
    resourceGroupName?: string;
    clusterName?: string;
  }) => {
    return await axiosInstance.get('/clusters', {
      params,
    });
  },
};
