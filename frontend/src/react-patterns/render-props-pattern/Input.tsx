const Input = (props: {
  renderLabel: (value) => React.ReactNode;
  renderLabel2: () => React.ReactNode;
  value: any;
  onChange: (e) => void;
}) => {
  return (
    <div>
      {props.renderLabel(props.value)}
      {props.renderLabel2()}
      <input
        type="text"
        onChange={(e) => {
          props.onChange(e.target.value);
        }}
        value={props.value}
      />
    </div>
  );
};

export default Input;
