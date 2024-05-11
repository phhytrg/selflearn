import { InboxOutlined, PlusOutlined } from '@ant-design/icons';
import { Button, Col, Row } from 'antd';
import Dragger from 'antd/es/upload/Dragger';
import OptionsSelect from './Select';
import { useSelect } from '../hooks/useSelect';

const CreateNodePoolsForm = () => {
  const params = useSelect();
  return (
    <>
      <OptionsSelect {...params} />
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
      <Row justify={'end'}>
        <Button
          type="primary"
          className="mt-3 mr-10 w-[100px]"
          icon={<PlusOutlined />}
        >
          Create
        </Button>
      </Row>
    </>
  );
};

export default CreateNodePoolsForm;
