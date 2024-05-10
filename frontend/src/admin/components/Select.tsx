import { AutoComplete, Row, Skeleton } from 'antd';
import {
  useGetClusters,
  useGetResources,
  useGetSubscriptions,
} from '../queries/useOptions';
import { useSelect } from '../hooks/useSelect';

const OptionsSelect = () => {
  const params = useSelect();
  const { data: subscriptions } = useGetSubscriptions();
  const { data: resources } = useGetResources(params.selectedSubscription);
  const { data: clusters, isLoading: isLoadingClusters } = useGetClusters(
    params.selectedSubscription,
    params.selectedResourceGroup,
  );
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
      />
      <AutoComplete
        popupMatchSelectWidth={200}
        style={{ width: 200 }}
        options={resources?.map((sub) => ({ value: sub.name }))}
        placeholder={'Resource groups'}
        onChange={(value) => {
          params.setSelectedResourceGroup(value);
        }}
      />
      <AutoComplete
        popupMatchSelectWidth={200}
        style={{ width: 200 }}
        options={clusters?.map((sub) => ({ value: sub.name }))}
        placeholder={'Clusters'}
        onChange={(value) => {
          params.setSelectedCluster(value);
        }}
        dropdownRender={(menu) => {
          return isLoadingClusters ? <Skeleton /> : menu;
        }}
      />
    </Row>
  );
};

export default OptionsSelect;