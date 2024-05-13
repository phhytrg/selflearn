import { nodePoolApi } from "@/shared/apis/nodepool.api";
import { useQuery } from "react-query";

export interface NodePool {
  readonly id?: string;
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

export const useGetNodePools = (
  params: {
    subscriptionName?: string;
    resourceGroupName?: string;
    clusterName?: string;
  },
  isFetchFromDb?: boolean,
) => {
  return useQuery<NodePool[], Error>([
    'nodePools',
    {
      params,
      isFetchFromDb,
    }
  ],{
    queryFn: async () => {
      return (await nodePoolApi.getNodePools(params)).data;
    },
    refetchOnWindowFocus: false,
  });
};
