import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<div>Home (Login)</div>} />
        <Route path="/dashboard" element={<div>Dashboard</div>} />
        <Route path="/search" element={<div>Search</div>} />
      </Routes>
    </Router>
  );
}
