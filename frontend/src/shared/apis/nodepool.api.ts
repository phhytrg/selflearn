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
    return await axiosInstance.delete('/node-pools/delete', { params });
  },
  upload: async (params: {
    subscriptionName: string;
    resourceGroupName: string;
    clusterName: string;
    file: File;
  }) => {
    const formData = new FormData();
    formData.append('file', params.file);
    formData.append('subscriptionName', params.subscriptionName);
    formData.append('resourceGroupName', params.resourceGroupName);
    formData.append('clusterName', params.clusterName);

    return await axiosInstance.post('/node-pools/upload', formData, {
      transformRequest: (data, headers) => {
        headers["Content-Type"] = "multipart/form-data";
        return data;
      },
    });
  }
};
