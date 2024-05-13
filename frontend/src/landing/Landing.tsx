import { Divider } from 'antd';
import { Link } from 'react-router-dom';

export const LandingPage = () => {
  return (
    <>
      <Link to="/home">Home</Link>
      <Divider/>
      <Link to="/admin">Admin</Link>
    </>
  );
};
