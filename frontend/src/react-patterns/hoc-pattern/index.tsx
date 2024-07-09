import DogImages from './DogImages';
import withStyles from './withStyles';
import withHover from './withHover';

export default function HocPattern() {
  const Item = withStyles(({ style }: { style: React.CSSProperties }) => (
    <div style={{ ...style, backgroundColor: 'yellow'}}>Item</div>
  ));
  

  return (
    <div>
      {/* <Item /> */}
      <DogImages />
    </div>
  );
}
