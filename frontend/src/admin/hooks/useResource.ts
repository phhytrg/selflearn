import { DeleteResourceResponse, nodePoolApi } from '@/shared/apis/nodepool.api';
import { useMutation } from 'react-query';

export const useDeleteResource = () => {
  return useMutation(
    async (params: {
      subscriptionName: string;
      resourceGroupName: string;
      clusterName: string;
    }) : Promise<DeleteResourceResponse> => {
      return (await nodePoolApi.delete(params)).data;
    },
  );
};

export const useCreateResource = () => {
  return useMutation(
    async (params: {
      subscriptionName: string;
      resourceGroupName: string;
      clusterName: string;
    }) => {
      // return await nodePoolApi.create(params);
    },
  );
};
