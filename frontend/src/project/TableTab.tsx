import { Table } from 'antd';

export const TableTab = () => {
  const columns = [
    'name',
    'provisionState',
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

  const dataSource = [
    {
      key: 1,
      name: 'hello',
      provisionState: 'a',
      powerState: 'a',
      nodeCount: 'a',
      mode: 'a',
      nodeImageVersion: 'a',
      k8sVersion: 'a',
      nodeSize: 'a',
      os: 'a',
    },
  ];

  return (
    <Table
      columns={columns}
      dataSource={dataSource}
      pagination={{
        position: ['bottomCenter'],
      }}
    />
  );
};
