import { Button, Col, Row } from 'antd';
import OptionsSelect from './Select';
import { DeleteOutlined } from '@ant-design/icons';
import { useDeleteResource as useDeleteResources } from '../hooks/useResource';
import { useSelect } from '../hooks/useSelect';

const DeleteForm = () => {
  const { mutate, data } = useDeleteResources();
  const params = useSelect();

  return (
    <div className="flex flex-col gap-2">
      <OptionsSelect key={"2"} {...params} />
      <Row gutter={[0, 10]} justify={'end'}>
        <Button
          type="primary"
          icon={<DeleteOutlined />}
          danger
          className="mr-10 w-[100px]"
          onClick={() => {
            mutate({
              subscriptionName: params.selectedSubscription,
              resourceGroupName: params.selectedResourceGroup,
              clusterName: params.selectedCluster,
            });
            params.reset();
          }}
        >
          Delete
        </Button>
      </Row>
      {!data || (
        <Row
          justify={'center'}
          className="bg-gray-100 max-w-[600px] m-auto p-2 rounded-lg shadow-md mt-2 text-green-600 font-semibold"
          align={'middle'}
        >
          <Col span={20}>
            <p>Number of subscriptions deleted: </p>
          </Col>
          <Col span={4}>
            <p>{data?.noSubscriptionsDeleted}</p>
          </Col>
          <Col span={20}>
            <p>Number of resource groups deleted: </p>
          </Col>
          <Col span={4}>
            <p>{data?.noResourceGroupsDeleted}</p>
          </Col>
          <Col span={20}>
            <p>Number of clusters deleted: </p>
          </Col>
          <Col span={4}>
            <p>{data?.noClustersDeleted}</p>
          </Col>
          <Col span={20}>
            <p>Number of node pools deleted: </p>
          </Col>
          <Col span={4}>
            <p>{data?.noNodePoolsDeleted}</p>
          </Col>
        </Row>
      )}
    </div>
  );
};

export default DeleteForm;
