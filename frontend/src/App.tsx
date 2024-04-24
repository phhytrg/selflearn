import './App.css';
import { BASE_URL, HOST_API } from './shared/constants/app';

function App() {
  console.log('BASE_URL', import.meta.env);
  console.log('HOST_API', HOST_API);
  return (
    <div>
      <h1>Host API: {HOST_API}</h1>
      <h1>Base URL: {BASE_URL}</h1>
    </div>
  );
}

export default App;
