import { InboxOutlined, PlusOutlined } from '@ant-design/icons';
import { Button, Col, Row } from 'antd';
import Dragger from 'antd/es/upload/Dragger';
import OptionsSelect from './Select';

const CreateNodePoolsForm = () => {
  return (
    <div>
      <OptionsSelect />
      <Row className="mt-2" align={'middle'} justify={'center'}>
        <Col span={20}>
          <Dragger>
            <p className="ant-upload-drag-icon">
              <InboxOutlined color="black" />
            </p>
            <p className="ant-upload-text">
              Click or drag file to this area to upload
            </p>
          </Dragger>
        </Col>
      </Row>
      <Row gutter={[0, 32]} justify={'end'}>
        <Button
          type="primary"
          className="mt-3 w-[100px]"
          icon={<PlusOutlined />}
        >
          Create
        </Button>
      </Row>
    </div>
  );
};

export default CreateNodePoolsForm;
