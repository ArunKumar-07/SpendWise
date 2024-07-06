import React from 'react';
import { Link } from 'react-router-dom';
import './Sidebar.css';

const Sidebar = ({ isSidebarOpen, toggleSidebar }) => {
  return (
    <nav className={`sidebar ${isSidebarOpen ? '' : 'close'}`}>
      <div className="menu_content">
        <ul className="menu_items">
          <li className="item">
            <Link to="/profile" className="nav_link submenu_item">
              <img 
                src="https://img.icons8.com/?size=100&id=3225&format=png&color=000000" 
                alt="Profile" 
                className="navlink_icon"
              />
              <span className="navlink">Profile</span>
              <i className="bx bx-chevron-right arrow-left"></i>
            </Link>
          </li>
          <li className="item">
            <Link to="/dashboard" className="nav_link submenu_item">
              <img 
                src="https://img.icons8.com/?size=100&id=xtnIDXkBb0sK&format=png&color=000000" 
                alt="Dashboard" 
                className="navlink_icon"
              />
              <span className="navlink">Dashboard</span>
              <i className="bx bx-chevron-right arrow-left"></i>
            </Link>
          </li>
          <li className="item">
            <Link to="/income" className="nav_link submenu_item">
              <img 
                src="https://img.icons8.com/?size=100&id=35072&format=png&color=000000" 
                alt="Income" 
                className="navlink_icon"
              />
              <span className="navlink">Income</span>
              <i className="bx bx-chevron-right arrow-left"></i>
            </Link>
          </li>
          <li className="item">
            <Link to="/expenses" className="nav_link submenu_item">
              <img 
                src="https://img.icons8.com/?size=100&id=YOapamJRIYEg&format=png&color=000000" 
                alt="Expense" 
                className="navlink_icon"
              />
              <span className="navlink">Expense</span>
              <i className="bx bx-chevron-right arrow-left"></i>
            </Link>
          </li>
        </ul>
        <div className="bottom_content">
          <div className="bottom collapse_sidebar" onClick={toggleSidebar}>
            <span>Collapse</span>
            <img 
              src="https://img.icons8.com/?size=100&id=eMQsFGlY7TA4&format=png&color=000000" 
              alt="Collapse icon" 
              className="collapse-icon"
            />
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Sidebar;