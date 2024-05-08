import { clusterApi, gitExchangeApi } from '@/shared/apis';
import { useQuery } from 'react-query';

export interface Cluster {
  readonly id: string;
  readonly name: string;
  readonly provisioningState: string;
  readonly powerState: string;
  readonly nodeCount: number;
  readonly mode: string;
  readonly nodeImageVersion: string;
  readonly k8sVersion: string;
  readonly nodeSize: string;
  readonly os: string;
}

const QUERY_KEY = 'clusters';

export const useGetClusters = (params: {
  subscriptionName?: string;
  resourceGroupName?: string;
  clusterName?: string;
}) => {
  return useQuery<Cluster[], Error>([QUERY_KEY, params], {
    queryFn: async () => {
      return (await clusterApi.getClusters(params)).data;
    },
    refetchOnWindowFocus: false,
  });
  // return useQuery<Cluster[], Error>([
  //   QUERY_KEY,
  //   params,
  //   async (params: {
  //     subscriptionName?: string;
  //     resourceGroupName?: string;
  //     clusterName?: string;
  //   }) => {
  //     return await clusterApi.getClusters(params);
  //   },
  // ]);
};
