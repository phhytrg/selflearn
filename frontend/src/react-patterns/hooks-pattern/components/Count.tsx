export function Count({
  count,
  increment,
  decrement,
}: {
  count: number;
  increment: () => void;
  decrement: () => void;
}) {
  return (
    <div className="counter">
      <div className="btns">
        <button onClick={increment}>+</button>
        <button onClick={decrement}>-</button>
      </div>
      <p>Current count: {count}</p>
    </div>
  );
}
