import { useHover } from './hooks/useHover';
import withHover from './withHover';
import withLoader from './withLoader';
import withStyles from './withStyles';

const DogImages = (props) => {
  const [hoverRef, hovering] = useHover();

  return (
    <div {...props} ref={hoverRef}>
      {hovering && <div id="hover">Hook Hovering!</div>}
      {props.hovering && <div id="hover">HOC Hovering!</div>}
      <div>
        {props.data.message.map((dog, index) => {
          return <img src={dog} key={index} />;
        })}
      </div>
    </div>
  );

  // const [hoverRef, hovering] = useHover();
  // return (
  //   <div ref={hoverRef} {...props}>
  //     {hovering && <div id="hover">Hovering!</div>}
  //     <div>
  //       {props.data.message.map((dog, index) => {
  //         return <img src={dog} key={index} />;
  //       })}
  //     </div>
  //   </div>
  // );
};

export default withLoader(
  // withStyles(DogImages),
  // withHover(DogImages),
  DogImages,
  'https://dog.ceo/api/breeds/image/random/10',
);
