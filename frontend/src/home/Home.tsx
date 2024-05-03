import { Tabs, TabsProps } from 'antd';
import { ProjectTab } from '../project/ProjectTab';

export const HomePage = () => {
  const items: TabsProps['items'] = [
    {
      key: '1',
      label: 'Project',
      children: <ProjectTab />,
    },
  ];

  return <Tabs className="h-full" items={items} defaultActiveKey="1" />;
};
