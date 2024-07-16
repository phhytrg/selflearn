import { Link, Navigate, Outlet } from 'react-router-dom';
import { useAuth } from './hooks/useAuth';
import { Button, Drawer } from 'antd';
import { useState } from 'react';
import { MenuOutlined } from '@ant-design/icons';
import { useRoles } from '@/shared/hooks/useAuthority';

export const ProtectedRoute = () => {
  const { isAdmin } = useRoles();
  const [open, setOpen] = useState(false);
  const { user, logout } = useAuth();

  const showDrawer = () => {
    setOpen(true);
  };

  const onClose = () => {
    setOpen(false);
  };

  if (!user) {
    //Try to get user from local storage
    return <Navigate to="/login" />;
  }

  return (
    <div className="flex flex-col">
      <Button
        className="ml-auto"
        aria-label="menu"
        icon={<MenuOutlined />}
        onClick={showDrawer}
      />
      <Drawer
        data-testid="drawer"
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
        {!isAdmin || <Link to={'/Admin'}>Admin</Link>}
        <a onClick={logout}>Log out</a>
      </Drawer>
      <Outlet />
    </div>
  );
};
