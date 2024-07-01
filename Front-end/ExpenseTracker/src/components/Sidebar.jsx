import React from 'react';
import { Link } from 'react-router-dom';
import './Sidebar.css';

const Sidebar = ({ isSidebarOpen, toggleSidebar }) => {
  return (
    <nav className={`sidebar ${isSidebarOpen ? '' : 'close'}`}>
      <div className="menu_content">
        <ul className="menu_items">
          <li className="item">
            <Link to="/dashboard" className="nav_link submenu_item">
              <span className="navlink">Dashboard</span>
              <i className="bx bx-chevron-right arrow-left"></i>
            </Link>
          </li>
          <li className="item">
            <Link to="/profile" className="nav_link submenu_item">
              <span className="navlink">Profile</span>
              <i className="bx bx-chevron-right arrow-left"></i>
            </Link>
          </li>
          <li className="item">
            <Link to="/expenses" className="nav_link submenu_item">
              <span className="navlink">Expense</span>
              <i className="bx bx-chevron-right arrow-left"></i>
            </Link>
          </li>
          <li className="item">
            <Link to="/income" className="nav_link submenu_item">
              <span className="navlink">Income</span>
              <i className="bx bx-chevron-right arrow-left"></i>
            </Link>
          </li>
        </ul>
        <div className="bottom_content">
          <div className="bottom expand_sidebar" onClick={toggleSidebar}>
            <span>Expand</span>
            <i className="bx bx-log-in"></i>
          </div>
          <div className="bottom collapse_sidebar" onClick={toggleSidebar}>
            <span>Collapse</span>
            <i className="bx bx-log-out"></i>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Sidebar;
