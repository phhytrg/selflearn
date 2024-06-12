import { AutoComplete, Row, Skeleton } from 'antd';
import {
  useGetClusters,
  useGetResources,
  useGetSubscriptions,
} from '../queries/useOptions';
import { useQueryClient } from 'react-query';

const OptionsSelect = (params: {
  selectedSubscription: string;
  setSelectedSubscription: (value: string) => void;
  selectedResourceGroup: string;
  setSelectedResourceGroup: (value: string) => void;
  selectedCluster: string;
  setSelectedCluster: (value: string) => void;
}) => {
  const { data: subscriptions, isLoading: isLoadingSubscriptions } = useGetSubscriptions();
  const { data: resources, isLoading: isLoadingResourceGroups } = useGetResources(params.selectedSubscription);
  const { data: clusters, isLoading: isLoadingClusters } = useGetClusters(
    params.selectedSubscription,
    params.selectedResourceGroup,
  );
  const queryClient = useQueryClient();
  return (
    <Row className="gap-2" justify={'center'}>
      <AutoComplete
        popupMatchSelectWidth={200}
        style={{ width: 200 }}
        options={subscriptions?.map((sub) => ({ value: sub.name }))}
        placeholder={'Subscription'}
        onChange={(value) => {
          params.setSelectedSubscription(value);
        }}
        onClick={() => {
          queryClient.invalidateQueries(['subscriptions']);
        }}
        allowClear
        dropdownRender={(menu) => {
          return isLoadingSubscriptions ? <Skeleton /> : menu;
        }}
      />
      <AutoComplete
        popupMatchSelectWidth={200}
        style={{ width: 200 }}
        options={resources?.map((sub) => ({ value: sub.name }))}
        placeholder={'Resource groups'}
        onChange={(value) => {
          params.setSelectedResourceGroup(value);
        }}
        onClick={() => {
          queryClient.invalidateQueries(['resources']);
        }}
        dropdownRender={(menu) => {
          return isLoadingResourceGroups ? <Skeleton /> : menu;
        }}
        allowClear
      />
      <AutoComplete
        popupMatchSelectWidth={200}
        style={{ width: 200 }}
        options={clusters?.map((sub) => ({ value: sub.name }))}
        placeholder={'Clusters'}
        onChange={(value) => {
          params.setSelectedCluster(value);
        }}
        allowClear
        dropdownRender={(menu) => {
          return isLoadingClusters ? <Skeleton /> : menu;
        }}
        onClick={() => {
          queryClient.invalidateQueries(['clusters']);
        }}
      />
    </Row>
  );
};

export default OptionsSelect;
