import { Select, Table } from 'antd';
import { useGetAllClusters } from './queries';
import { useGetAllSubscriptions } from './queries/useSubscription';
import { useGetAllResourceGroups } from './queries/useResouceGroup';

export const TableTab = () => {
  const columns = [
    'name',
    'provisioningState',
    'powerState',
    'nodeCount',
    'mode',
    'nodeImageVersion',
    'k8sVersion',
    'nodeSize',
    'os',
  ].map((item) => {
    return {
      title: item,
      dataIndex: item,
      key: item,
    };
  });

  const { isLoading, data: clusters } = useGetAllClusters();
  const { data: subscriptions } = useGetAllSubscriptions();
  const { data: resourceGroups } = useGetAllResourceGroups();

  return (
    <div>
      <div className="flex flex-row gap-3 justify-center">
        <Select
          options={subscriptions?.map(
            (subscription: { name: string; id: string }) => {
              return {
                key: subscription.name,
                value: subscription.name,
              };
            },
          )}
          className="text-center"
          placeholder={'Subscription'}
          allowClear
        />
        <Select
          options={resourceGroups?.map(
            (resourceGroup: { name: string; id: string }) => {
              return {
                key: resourceGroup.name,
                value: resourceGroup.name,
              };
            },
          )}
          className="text-center"
          placeholder={'Resource group'}
          allowClear
        />
        <Select
          options={clusters?.map((cluster) => {
            return {
              key: cluster.name,
              value: cluster.name,
            };
          })}
          className="text-center"
          placeholder={'Cluster'}
          allowClear
        />
      </div>
      <Table
        columns={columns}
        dataSource={clusters?.map((i) => {
          return { ...i, key: i.name };
        })}
        pagination={{
          position: ['bottomCenter'],
          hideOnSinglePage: true,
        }}
        loading={isLoading}
      />
    </div>
  );
};
