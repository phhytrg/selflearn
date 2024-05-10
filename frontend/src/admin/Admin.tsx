import { Col, Row, Tabs, TabsProps } from 'antd';
import CreateNodePoolsForm from './components/CreateNodePoolsForm';
import SyncButtons from './components/SyncButtons';
import DeleteForm from './components/DeleteForm';

export const AdminPage = () => {
  const items: TabsProps['items'] = [
    {
      key: '/admin/create-node-pools',
      label: 'Create Node Pools',
      children: <CreateNodePoolsForm />,
    },
    {
      key: '/admin/delete',
      label: 'Delete',
      children: <DeleteForm />,
    },
  ];
  return (
    <Row gutter={[16, 8]} align={'middle'} justify={'center'}>
      <SyncButtons />

      <Col
        span={24}
        className="h-[600px] bg-white shadow-md m-2 max-w-[800px] p-2 rounded-lg"
      >
        <Tabs items={items} />
      </Col>
    </Row>
  );
};
