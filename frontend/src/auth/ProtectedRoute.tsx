import { Link, Navigate, Outlet } from 'react-router-dom';
import { useAuth } from './hooks/useAuth';
import { Button, Drawer } from 'antd';
import { useState } from 'react';
import { MenuOutlined } from '@ant-design/icons';

export const ProtectedRoute = () => {
  const [open, setOpen] = useState(false);

  const showDrawer = () => {
    setOpen(true);
  };

  const onClose = () => {
    setOpen(false);
  };

  const { user } = useAuth();

  if (!user) {
    //Try to get user from local storage
    return <Navigate to="/login" />;
  }
  return (
    <div className="flex flex-col">
      <Button
        className="ml-auto"
        icon={<MenuOutlined />}
        onClick={showDrawer}
      />
      <Drawer
        onClose={onClose}
        open={open}
        styles={{
          body: {
            display: 'flex',
            flexDirection: 'column',
            gap: '8px',
          },
        }}
      >
        <Link to={'/Home'}>Home</Link>
        <Link to={'/Admin'}>Admin</Link>
      </Drawer>
      <Outlet />
    </div>
  );
};
