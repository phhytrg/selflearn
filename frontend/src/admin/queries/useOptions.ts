import { clusterApi, resourceGroupApi, subscriptionApi } from '@/shared/apis';
import { useDebounce } from '@uidotdev/usehooks';
import { useQuery } from 'react-query';

const SUB_KEY = 'subscriptions';
const RES_KEY = 'resources';
const CLUSTER_KEY = 'clusters';

export const useGetSubscriptions = () => {
  return useQuery<{ name: string }[]>([SUB_KEY], {
    queryFn: async () => {
      return (await subscriptionApi.getAll()).data;
    },
  });
};

export const useGetResources = (subscriptionName: string) => {
  const debounceSubscription = useDebounce(subscriptionName, 500);
  return useQuery<{ name: string }[]>([RES_KEY, debounceSubscription], {
    queryFn: async () => {
      return (await resourceGroupApi.getBySubscriptionName(subscriptionName))
        .data;
    },
  });
};

export const useGetClusters = (
  subscriptionName: string,
  resourceGroupName: string,
) => {
  const debounceSubscription = useDebounce(subscriptionName, 500);
  const debounceResourceGroup = useDebounce(resourceGroupName, 500);
  return useQuery<{ name: string }[]>(
    [CLUSTER_KEY, debounceSubscription, debounceResourceGroup],
    {
      queryFn: async () => {
        return (
          await clusterApi.getClusters({
            subscriptionName,
            resourceGroupName,
          })
        ).data;
      },
    },
  );
};
