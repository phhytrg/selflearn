import { useEffect, useRef, useState } from 'react';

export const useHover = () => {
  const [hovering, setHover] = useState(false);
  const ref = useRef<Element>(null);

  const onMouseOver = () => {
    setHover(true);
  };

  const onMouseOut = () => {
    setHover(false);
  };

  useEffect(() => {
    const node = ref.current; // Add type assertion here
    if (node) {
      node.addEventListener('mouseover', onMouseOver);
      node.addEventListener('mouseout', onMouseOut);
    }
  }, [ref.current]);

  return [ref, hovering];
};
