import { resourceGroupApi } from '@/shared/apis';
import { useQuery } from 'react-query';

export const useGetResourceGroupsBySubscription = (
  subscriptionName?: string,
) => {
  return useQuery<
    {
      id: string;
      name: string;
    }[],
    Error
  >(['resources', subscriptionName], {
    queryFn: async () => {
      return (await resourceGroupApi.getBySubscriptionName(subscriptionName))
        .data;
    },
  });
};
