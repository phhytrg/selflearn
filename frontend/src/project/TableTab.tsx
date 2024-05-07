import { Table } from 'antd';
import { useGetAllClusters } from './queries';

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

  return (
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
  );
};
