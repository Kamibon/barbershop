import { useState, type ReactElement } from "react";
import { Homepage } from "./pages/homepage";
import { Layout } from "./pages/layout";
import { WhoAreWe } from "./pages/whoAreWe";
import { Appointments } from "./pages/appointments";
import { Service } from "./pages/service";

const tabMap = new Map<number, ReactElement>([
  [0, <Homepage />],
  [1, <Service />],
  [2, <Appointments />],
  [3, <WhoAreWe />],
]);

export const bUrl = 'http://localhost:8081'

function App() {
  const [tab, setTab] = useState(0);

  return (
    <div className="min-h-screen">
      <Layout onChangeTab={(value) => setTab(value)}>{tabMap.get(tab)!}</Layout>
    </div>
  );
}

export default App;
