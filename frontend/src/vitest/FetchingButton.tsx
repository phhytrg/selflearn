import { useState } from 'react';

const FetchButton = ({ fetchData }) => {
  const [data, setData] = useState(null);

  const handleClick = async () => {
    const { data } = await fetchData();
    setData(data);
  };

  return (
    <div>
      <button onClick={handleClick}>Fetch Data</button>
      {data && <div>{data}</div>}
    </div>
  );
};

export default FetchButton;
