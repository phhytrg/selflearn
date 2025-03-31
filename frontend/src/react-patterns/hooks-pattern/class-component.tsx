import React from 'react';
import { Count } from './components';
import { Width } from './components';
type CounterState = {
  count: number;
  width: number;
};

export default class ClassCounter extends React.Component<object, CounterState> {
  constructor(props: object) {
    super(props);
    this.state = {
      count: 0,
      width: 0,
    };
  }

  componentDidMount() {
    this.handleResize();
    window.addEventListener('resize', this.handleResize);
  }
  componentWillUnmount() {
    window.removeEventListener('resize', this.handleResize);
  }
  
  increment = () => { this.setState(({ count }) => ({ count: count + 1 }));};
  decrement = () => { this.setState(({ count }) => ({ count: count - 1 }));};

  handleResize = () => { this.setState({ width: window.innerWidth });};

  render() {
    return (
      <div className="App">
        <Count
          count={this.state.count}
          increment={this.increment}
          decrement={this.decrement}
        />
        <div id="divider" />
        <Width width={this.state.width} />
      </div>
    );
  }
}
