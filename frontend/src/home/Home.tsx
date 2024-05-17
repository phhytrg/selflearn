import { Tabs, TabsProps } from 'antd';
import { ProjectTab } from '../project/ProjectTab';
import { TableTab } from '@/project/TableTab';
import { useLocation, useNavigate } from 'react-router-dom';

export const HomePage = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const items: TabsProps['items'] = [
    {
      key: '/home/project',
      label: 'Project',
      children: <ProjectTab />,
    },
    {
      key: '/home/table',
      label: 'Table',
      children: <TableTab />,
    },
  ];

  return (
    <Tabs
      className="h-full min-h-[100vh]"
      items={items}
      defaultActiveKey="project"
      activeKey={items.find((item) => item.key === location.pathname)?.key}
      onChange={(path) => {
        navigate(`${path}`, { replace: true });
      }}
    />
  );
};
