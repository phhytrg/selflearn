import { ReactNode, useState } from 'react';
import Input from './Input';
import Input2 from './Input2';

function Kelvin({ value }) {
  return <div className="temp">{parseInt(value || 0) + 273.15}K</div>;
}

function Fahrenheit({ value }) {
  return <div className="temp">{(parseInt(value || 0) * 9) / 5 + 32}°F</div>;
}

// export default function LiftTheState() {
//   const [value, setValue] = useState('');

//   return (
//     <div className="App">
//       <h1>☃️ Temperature Converter 🌞</h1>
//       <Input
//         value={value}
//         onChange={setValue}
//         renderLabel={function (): ReactNode {
//           return <label>Enter temperature in Celsius:</label>;
//         }}
//       />
//       <Kelvin value={value} />
//       <Fahrenheit value={value} />
//     </div>
//   );
// }

/** Replace with render props */

// export default function LiftTheState() {
//   const [value, setValue] = useState('');

//   return (
//     <div className="App">
//       <h1>☃️ Temperature Converter 🌞</h1>
//       <Input2
//         render={function (value: any): ReactNode {
//           return (
//             <>
//               <Fahrenheit value={value} />
//               <Kelvin value={value} />
//             </>
//           );
//         }}
//       />
//     </div>
//   );
// }

// Replace with render props as child
const Input3 = (props) => {
  const [value, setValue] = useState<string>('');

  return (
    <>
      <input
        value={value}
        onChange={(e) => {
          setValue(e.target.value);
        }}
      />
      {props.children(value)}
    </>
  );
};

export default function LiftTheState() {

  return (
    <div className="App">
      <h1>☃️ Temperature Converter 🌞</h1>
      <Input3>
        {(value) => (
          <>
            <Fahrenheit value={value} />
            <Kelvin value={value} />
          </>
        )}
      </Input3>
    </div>
  );
}
