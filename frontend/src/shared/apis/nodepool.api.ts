import axiosInstance from './axios-config';

export const nodePoolApi = {
  getNodePools: async (params: {
    subscriptionName?: string;
    resourceGroupName?: string;
    clusterName?: string;
  }) => {
    return await axiosInstance.get('/node-pools', { params });
  },
};
