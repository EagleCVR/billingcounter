import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import LoginPage from "./pages/LoginPage";
import Dashboard from "./pages/Dashboard"; // placeholder
// Import other pages...

function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/dashboard" element={<Dashboard />} />
        {/* Add more routes */}
      </Routes>
    </Router>
  );
}

export default App;
