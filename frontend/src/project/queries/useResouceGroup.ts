import { gitExchangeApi, resourceGroupApi } from '@/shared/apis';
import { useQuery } from 'react-query';

export const useGetResourceGroupsBySubscription = (
  subscriptionName?: string,
  fetchFromDb?: boolean,
) => {
  return useQuery<
    {
      id: string;
      name: string;
    }[],
    Error
  >(['resources', subscriptionName], {
    queryFn: async () => {
      if (!fetchFromDb) {
        return (await gitExchangeApi.getResourceGroups(subscriptionName)).data;
      }
      return (await resourceGroupApi.getBySubscriptionName(subscriptionName))
        .data;
    },
    refetchOnWindowFocus: false,
  });
};
