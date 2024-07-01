import React from 'react';
import './DarkModeToggle.css'; 
import { useColorScheme } from './useColorScheme';

const DarkModeToggle = () => {
  const { isDark, setIsDark } = useColorScheme();

  return (
    <div className="dark-mode-toggle">
      <span className="material-icons light-mode-icon">wb_sunny</span>
      <label className="switch">
        <input type="checkbox" checked={isDark} onChange={({ target }) => setIsDark(target.checked)} />
        <span className="slider round"></span>
      </label>
      <span className="material-icons dark-mode-icon">nightlight_round</span>
    </div>
  );
};

export default DarkModeToggle;
