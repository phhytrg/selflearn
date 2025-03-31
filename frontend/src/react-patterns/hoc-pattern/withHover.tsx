import React, { useState } from 'react';
import { JSX } from 'react/jsx-runtime';

export default function withHover(Element: React.ComponentType<any>) {
  return (props: JSX.IntrinsicAttributes) => {
    const [hovering, setHover] = useState(false);

    return (
      <Element
        {...props}
        hovering={hovering}
        onMouseEnter={() => setHover(true)}
        onMouseLeave={() => setHover(false)}
      />
    );
  };
}
