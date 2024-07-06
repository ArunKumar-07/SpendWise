import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Profile.css';

const Profile = () => {
  const [user, setUser] = useState(null);
  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [showDeleteModal, setShowDeleteModal] = useState(false);

  useEffect(() => {
    fetchUserData();
  }, []);

  const fetchUserData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/new/1');
      setUser(response.data);
    } catch (error) {
      console.error('Error fetching user data:', error);
    }
  };

  const handleUpdate = async () => {
    try {
      await axios.put(`http://localhost:8080/new/update/${user.id}`, user);
      setShowUpdateModal(false);
      fetchUserData();
    } catch (error) {
      console.error('Error updating user data:', error);
    }
  };

  const handleDelete = async () => {
    try {
      await axios.delete(`http://localhost:8080/new/delete/${user.id}`);
      setShowDeleteModal(false);
      // Redirect to home page or login page after deletion
    } catch (error) {
      console.error('Error deleting user account:', error);
    }
  };

  if (!user) return <div>Loading...</div>;

  return (
    <div className="profile-container">
      <h1 className="account-details">Your Account Details</h1>
      <div className="profile-info">
        <div className="info-group">
          <div className="input-wrapper">
            <label>Username</label>
            <input type="text" value={user.username} readOnly />
          </div>
          <div className="input-wrapper">
            <label>Email</label>
            <input type="email" value={user.email} readOnly />
          </div>
        </div>
        <div className="info-group">
          <div className="input-wrapper">
            <label>Balance</label>
            <input type="text" value={`₹${user.balance}`} readOnly className="balance" />
          </div>
        </div>
      </div>
      <div className="button-group">
        <button className="update-btn" onClick={() => setShowUpdateModal(true)}>Update Your Account</button>
        <button className="change-pwd-btn" onClick={() => {}}>Change Password</button>
        <button className="delete-btn" onClick={() => setShowDeleteModal(true)}>Delete Your Account</button>
      </div>

      {showUpdateModal && (
        <div className="modal">
          <h2>Update Your Account</h2>
          <div className="input-wrapper">
            <label>Username</label>
            <input
              type="text"
              value={user.username}
              onChange={(e) => setUser({ ...user, username: e.target.value })}
            />
          </div>
          <div className="input-wrapper">
            <label>Email</label>
            <input
              type="email"
              value={user.email}
              onChange={(e) => setUser({ ...user, email: e.target.value })}
            />
          </div>
          <div className="modal-buttons">
            <button className="update-btn" onClick={handleUpdate}>Update</button>
            <button className="cancel-btn" onClick={() => setShowUpdateModal(false)}>Cancel</button>
          </div>
        </div>
      )}

      {showDeleteModal && (
        <div className="modal">
          <h2>Delete Your Account</h2>
          <p>Are you sure you want to delete your account?</p>
          <div className="modal-buttons">
            <button className="delete-btn" onClick={handleDelete}>Delete</button>
            <button className="cancel-btn" onClick={() => setShowDeleteModal(false)}>Cancel</button>
          </div>
        </div>
      )}
    </div>
  );
};

export default Profile;