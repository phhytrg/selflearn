import { Tabs, TabsProps } from 'antd';
import { ProjectTab } from '../project/ProjectTab';
import { TableTab } from '@/project/TableTab';
import { useNavigate } from 'react-router-dom';

export const HomePage = () => {
  const navigate = useNavigate();

  const items: TabsProps['items'] = [
    {
      key: 'project',
      label: 'Project',
      children: <ProjectTab />,
    },
    {
      key: 'table',
      label: 'Table',
      children: <TableTab />,
    },
  ];

  return (
    <Tabs
      className="h-full min-h-[100vh]"
      items={items}
      defaultActiveKey="1"
      onChange={(path) => {
        navigate(`${path}`, { replace: true });
      }}
    />
  );
};
