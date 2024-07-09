import { CheckCircleFilled } from '@ant-design/icons';
import { Input } from 'antd';

export default function RenderPropsUseCase() {
  return (
    <>
      <Input.Password
        iconRender={(visible) => {
          return <p>Show</p>;
        }}
      />
    </>
  );
}
