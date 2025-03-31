import { InboxOutlined, PlusOutlined } from '@ant-design/icons';
import { Button, Col, Row } from 'antd';
import Dragger from 'antd/es/upload/Dragger';
import OptionsSelect from './Select';
import { useSelect } from '../hooks/useSelect';
import { useState } from 'react';
import { nodePoolApi } from '@/shared/apis/nodepool.api';

const CreateNodePoolsForm = () => {
  const [file, setFile] = useState<File | null>(null);
  const params = useSelect();

  const handleCreate = async () => {
    await nodePoolApi.upload({
      subscriptionName: params.selectedSubscription,
      resourceGroupName: params.selectedResourceGroup,
      clusterName: params.selectedCluster,
      file: file!,
    });

    alert('Node pool created successfully');
  };

  return (
    <>
      <OptionsSelect key={"1"} {...params} />
      <Row className="mt-2" align={'middle'} justify={'center'}>
        <Col span={20}>
          <Dragger
            name="file"
            multiple={false}
            action={undefined}
            beforeUpload={(f) => {
              setFile(f);
              return false;
            }}
          >
            <p className="ant-upload-drag-icon">
              <InboxOutlined color="black" />
            </p>
            <p className="ant-upload-text">
              Click or drag file to this area to upload
            </p>
          </Dragger>
        </Col>
      </Row>
      <Row justify={'end'}>
        <Button
          type="primary"
          className="mt-3 mr-10 w-[100px]"
          icon={<PlusOutlined />}
          onClick={handleCreate}
        >
          Create
        </Button>
      </Row>
    </>
  );
};

export default CreateNodePoolsForm;
