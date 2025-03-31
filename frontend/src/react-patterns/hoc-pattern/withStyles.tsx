import { CSSProperties } from 'react';
import { JSX } from 'react/jsx-runtime';

export default function withStyles(Component: JSX.ElementType) {
  return (props: JSX.IntrinsicAttributes) => {
    const style: CSSProperties = {
      padding: '3rem',
      margin: '2rem',
      backgroundColor: 'red',
    };
    return <Component style={style} {...props} />;
  };
}
