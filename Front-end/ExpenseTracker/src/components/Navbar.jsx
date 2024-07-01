import React from 'react';
import DarkModeToggle from '../DarkModeToggle';
import './Navbar.css';

const Navbar = ({ toggleSidebar, isDarkMode }) => {
  return (
    <nav className={`navbar ${isDarkMode ? 'dark' : 'light'}`}>
      <div className="logo_item">
        <i className="bx bx-menu" id="sidebarOpen" onClick={toggleSidebar}></i>
        Expense Tracker
      </div>
      <div className="navbar_content">
        <DarkModeToggle />
      </div>
    </nav>
  );
};

export default Navbar;
