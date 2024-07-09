import { Divider } from 'antd';
import { Link } from 'react-router-dom';

const Item = () => {
  return <div className="p-10 bg-red-400 m-2 rounded-sm">Item to display</div>;
};

const ReactPatternsPage = () => {
  return (
    <div className="m-16">
      <div className="flex flex-row flex-wrap m-auto">
        {/* <Link to="/react-patterns/hooks-pattern">Hooks Pattern</Link>
      <Divider />
      <Link to="/react-patterns/hoc-pattern">HOC Pattern</Link>
      <Divider />
      <Link to={"/react-patterns/render-props-pattern"}>Render Props Pattern</Link> */}
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
        <Item />
      </div>
    </div>
  );
};

export default ReactPatternsPage;
