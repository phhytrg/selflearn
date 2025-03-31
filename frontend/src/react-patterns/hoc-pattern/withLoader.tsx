import { useEffect, useState } from 'react';

export default function withLoader(
  Element: React.ComponentType<any>,
  url: string | URL | Request,
) {
  return (props: JSX.IntrinsicAttributes) => {
    const [data, setData] = useState(null);

    useEffect(() => {
      fetch(url)
        .then((res) => res.json())
        .then((data) => setData(data));
    }, []);

    if (!data) {
      return <div>Loading...</div>;
    }

    return <Element {...props} data={data} />;
  };
}
