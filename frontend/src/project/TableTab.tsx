import { Select, Switch, Table } from 'antd';
import { useGetAllSubscriptions } from './queries/useSubscription';
import { useState } from 'react';
import { useGetResourceGroupsBySubscription } from './queries/useResouceGroup';
import { useGetClusters } from './queries';
import { useAuth } from '@/auth/hooks/useAuth';
import { DatabaseFilled, GithubFilled } from '@ant-design/icons';

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

  const { user } = useAuth();
  const [isFetchFromDb, setIsFetchFromDb] = useState<boolean>(false);
  const [selectedSubscription, setSelectedSubscription] = useState<string>();
  const [selectedResource, setSelectedResource] = useState<string>();
  const [selectedCluster, setSelectedCluster] = useState<string>();
  const { isLoading, data: clusters } = useGetClusters(
    {
      clusterName: selectedCluster,
      subscriptionName: selectedSubscription,
      resourceGroupName: selectedResource,
    },
    isFetchFromDb,
  );
  const { data: subscriptions } = useGetAllSubscriptions();
  const { data: resourceGroups } =
    useGetResourceGroupsBySubscription(selectedSubscription);
  return (
    <div>
      <div className="flex flex-row gap-3 justify-center items-center">
        <Select
          options={subscriptions?.map(
            (subscription: { name: string; id: string }) => {
              return {
                key: subscription.name,
                value: subscription.name,
              };
            },
          )}
          value={selectedSubscription}
          className="text-center"
          placeholder={'Subscription'}
          allowClear
          onChange={(value) => {
            setSelectedSubscription(value);
            if (selectedResource != undefined) {
              setSelectedResource(undefined);
            }
            if (selectedCluster != undefined) {
              setSelectedCluster(undefined);
            }
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
          value={selectedResource}
          className="text-center"
          placeholder={'Resource group'}
          allowClear
          onChange={(value) => {
            setSelectedResource(value);
            if (selectedCluster != undefined) {
              setSelectedCluster(undefined);
            }
          }}
        />
        <Select
          options={clusters?.map((cluster) => {
            return {
              key: cluster.name,
              value: cluster.name,
            };
          })}
          value={selectedCluster}
          className="text-center"
          placeholder={'Cluster'}
          allowClear
          onChange={(value) => {
            setSelectedCluster(value);
          }}
        />
        {!user?.roles.map((role) => role.authority).includes('ADMIN') || (
          <div className="flex flex-row text-center items-center">
            <GithubFilled /> &nbsp;
            <Switch
              value={isFetchFromDb}
              onChange={(value) => {
                setIsFetchFromDb(value);
                setSelectedCluster(undefined);
                setSelectedResource(undefined);
                setSelectedSubscription(undefined);
              }}
            />
            &nbsp;
            <DatabaseFilled />
          </div>
        )}
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
