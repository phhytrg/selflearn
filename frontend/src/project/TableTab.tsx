import { Select, Table } from 'antd';
import { useGetAllSubscriptions } from './queries/useSubscription';
import { useState } from 'react';
import { useGetResourceGroupsBySubscription } from './queries/useResouceGroup';
import { useGetClusters } from './queries';

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

  const [selectedSubscription, setSelectedSubscription] = useState<string>();
  const [selectedResource, setSelectedResource] = useState<string>();
  const [selectedCluster, setSelectedCluster] = useState<string>();
  const { isLoading, data: clusters } = useGetClusters({
    clusterName: selectedCluster,
    subscriptionName: selectedSubscription,
    resourceGroupName: selectedResource,
  });
  const { data: subscriptions } = useGetAllSubscriptions();
  const { data: resourceGroups } =
    useGetResourceGroupsBySubscription(selectedSubscription);

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
          onChange={(value) => {
            setSelectedSubscription(value);
          }}
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
          onChange={(value) => {
            setSelectedResource(value);
          }}
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
          onChange={(value) => {
            setSelectedCluster(value);
          }}
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
