import { Button, Row } from 'antd';
import OptionsSelect from './Select';

const DeleteForm = () => {
  return (
    <div className='flex flex-col gap-2'>
      <OptionsSelect />
      <Row gutter={[0,10]}  justify={'end'}>
        <Button type='primary' danger>
          Delete
        </Button>
      </Row>
    </div>
  );
};

export default DeleteForm;
