import { ReactNode, useState } from 'react';

export default function Input2(props: { render: (value) => ReactNode }) {
  const [value, setValue] = useState<string>('');

  return (
    <>
      {props.render(value)}
      <input
        value={value}
        onChange={(e) => {
          setValue(e.target.value);
        }}
      />
    </>
  );
}
