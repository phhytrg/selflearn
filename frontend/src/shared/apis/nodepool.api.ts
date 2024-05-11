import axiosInstance from './axios-config';

export interface DeleteResourceResponse {
  noSubscriptionsDeleted: boolean;
  noResourceGroupsDeleted: boolean;
  noClustersDeleted: boolean;
  noNodePoolsDeleted: boolean;
}

export const nodePoolApi = {
  getNodePools: async (params: {
    subscriptionName?: string;
    resourceGroupName?: string;
    clusterName?: string;
  }) => {
    return await axiosInstance.get('/node-pools', { params });
  },
  delete: async (params: {
    subscriptionName: string;
    resourceGroupName: string;
    clusterName: string;
  }) => {
    return (await axiosInstance.delete('/node-pools/delete', { params })); 
  },
};
