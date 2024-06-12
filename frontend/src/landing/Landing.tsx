import { useRoles } from '@/shared/hooks/useAuthority';
import { Divider } from 'antd';
import { Link } from 'react-router-dom';

export const LandingPage = () => {
  const { isAdmin } = useRoles();
  return (
    <>
      <Link to="/home">Home</Link>
      {!isAdmin || (
        <>
          <Divider />
          <Link to={'/Admin'}>Admin</Link>
        </>
      )}
      <Divider />
      <Link to="/react-patterns">React Patterns</Link>
    </>
  );
};
