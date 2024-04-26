import './App.css';
import { HOST_API, BASE_URL } from '@constants';

function App() {
  return (
    <div>
      <h1>Host API: {HOST_API}</h1>
      <h1>Base URL: {BASE_URL}</h1>
    </div>
  );
}

export default App;
