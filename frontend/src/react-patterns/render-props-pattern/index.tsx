import { ReactNode, useState } from 'react';
import Input from './Input';
import { Select } from 'antd';

function Kelvin({ value }) {
  return <div className="temp">{parseInt(value || 0) + 273.15}K</div>;
}

function Fahrenheit({ value }) {
  return <div className="temp">{(parseInt(value || 0) * 9) / 5 + 32}°F</div>;
}

export default function RenderPropsPatternPage() {
  const [value, setValue] = useState();
  return (
    <div>
      <div className="App">
        <h1>☃️ Temperature Converter 🌞</h1>
        {/* <Input
          value={value}
          onChange={setValue}
          renderLabel={function (): ReactNode {
            return <label>Enter temperature in Celsius:</label>;
          }}
          renderLabel2={function (): ReactNode {
            throw new Error('Function not implemented.');
          }}
        >
          {(value) => {
            return (
              <>
                <Kelvin value={value} />
                <Fahrenheit value={value} />
              </>
            );
          }}
        </Input> */}
      </div>
      {/* <Input
        renderLabel={(value) => {
          return <p>{value}</p>;
        }}
        renderLabel2={() => {
          return <p>Label 2</p>;
        }}
        value={value}
        onChange={function (e): void {
          setValue(e);
        }}
      /> */}
      {/* <Input
        renderLabel={function (): ReactNode {
          return (
            <div>
              <p>Label 2</p>
              <p>Label 3</p>
            </div>
          );
        }}
        value={undefined}
        onChange={function (): void {
          throw new Error('Function not implemented.');
        }}
      /> */}

      {/* <LiftTheState /> */}

      {/* <RenderPropsUseCase /> */}
      <Select
        mode='multiple'
        value={['test']}
        options={[{
          label: 'test1',
          value: 'test',
          key: 'test'
        }]}
      />
    
    </div>
  );
}
