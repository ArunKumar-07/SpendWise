import React, { useEffect, useState } from "react";
import axios from '../axiosConfig'; 
import "./Expense.css";

function Form({ onSubmitSuccess }) {
  const [formData, setFormData] = useState({
    amount: "",
    date: "",
    modeOfPayment: "",
    remarks: "",
    category: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post("http://localhost:8080/new/create/Expense", formData);
      onSubmitSuccess();
      setFormData({
        amount: "",
        date: "",
        modeOfPayment: "",
        remarks: "",
        category: "",
      });
    } catch (error) {
      console.error("There was an error submitting the form!", error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-row">
        <div className="form-group">
          <label>User ID</label>
          <input name="userId" value={formData.userId} onChange={handleChange} />
        </div>
        <div className="form-group">
          <label>Amount</label>
          <input
            name="amount"
            type="number"
            value={formData.amount}
            onChange={handleChange} 
          />
        </div>
      </div>
      <div className="form-row">
        <div className="form-group">
          <label>Date</label>
          <input
            name="date"
            type="date"
            value={formData.date}
            onChange={handleChange}
            onFocus={(e) => e.target.showPicker()}
          />
        </div>  
        <div className="form-group">
          <label>Mode of Payment</label>
          <input
            name="modeOfPayment"
            value={formData.modeOfPayment}
            onChange={handleChange}
          />
        </div>
      </div>
      <div className="form-row">
        <div className="form-group full-width">
          <label>Remarks</label>
          <input
            name="remarks"
            value={formData.remarks}
            onChange={handleChange}
          />
        </div>
      </div>
      <div className="form-row">
        <div className="form-group full-width">
          <label>Category</label>
          <select
            name="category"
            value={formData.category}
            onChange={handleChange}
          >
            <option value="">Select category</option>
            <option value="shopping">Shopping</option>
            <option value="electricity">Electricity</option>
            <option value="EMI">EMI</option>
            <option value="bills">Bills</option>
            <option value="investment">Investment</option>
            <option value="groceries">Groceries</option>
            <option value="travel">Travel</option>
            <option value="petrol">Petrol</option>
            <option value="others">Others</option>
          </select>
        </div>
      </div>
      <input type="submit" className="submitButton" value="Submit" />
    </form>
  );
}

function ExpenseTable({ onUpdateSuccess, onDeleteSuccess }) {
  const [transactions, setTransactions] = useState([]);
  const [showPopup, setShowPopup] = useState(false);
  const [showDeletePopup, setShowDeletePopup] = useState(false);
  const [currentTransaction, setCurrentTransaction] = useState(null);

  useEffect(() => {
    fetchTransactions();
    return () => {
      document.body.classList.remove('popup-open');
    };
  }, []);

  useEffect(() => {
    if (showPopup || showDeletePopup) {
      document.body.classList.add('popup-open');
    } else {
      document.body.classList.remove('popup-open');
    }
  }, [showPopup, showDeletePopup]);

  const fetchTransactions = async () => {
    try {
      const response = await axios.get("http://localhost:8080/new/get/Expense");
      setTransactions(response.data);
    } catch (error) {
      console.error("There was an error fetching the transactions!", error);
    }
  };

  const handleEdit = (transaction) => {
    setCurrentTransaction(transaction);
    setShowPopup(true);
  };

  const handleDelete = (transaction) => {
    setCurrentTransaction(transaction);
    setShowDeletePopup(true);
  };

  const confirmDelete = async () => {
    try {
      await axios.delete(`http://localhost:8080/new/delete/Expense/${currentTransaction.id}`);
      setShowDeletePopup(false);
      fetchTransactions();
      onDeleteSuccess();
    } catch (error) {
      console.error("There was an error deleting the transaction!", error);
    }
  };

  const handleUpdate = async () => {
    try {
      await axios.put(`http://localhost:8080/new/update/Expense/${currentTransaction.id}`, currentTransaction);
      setShowPopup(false);
      fetchTransactions();
      onUpdateSuccess();
    } catch (error) {
      console.error("There was an error updating the transaction!", error);
    }
  };

  const handleInputChange = (e) => {
    setCurrentTransaction({
      ...currentTransaction,
      [e.target.name]: e.target.value
    });
  };

  return (
    <div className={`table-container ${showPopup || showDeletePopup ? 'blur-background' : ''}`}>
      <h2 className="Expense-transactions-heading">Expense Transactions</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Amount</th>
            <th>Date</th>
            <th>Mode of Payment</th>
            <th>Remarks</th>
            <th>Category</th>
            <th>Current Balance</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {transactions.map((transaction) => (
            <tr key={transaction.id}>
              <td>{transaction.id}</td>
              <td className="amount">₹{transaction.amount}</td>
              <td>{transaction.date}</td>
              <td>{transaction.modeOfPayment}</td>
              <td>{transaction.remarks}</td>
              <td>{transaction.category}</td>
              <td className="current-balance">₹{transaction.currentBalance}</td>
              <td>
                <button className="icon-button edit" onClick={() => handleEdit(transaction)}>
                  <img src="https://img.icons8.com/?size=100&id=18709&format=png&color=000000" alt="Edit" />
                </button>
                <button className="icon-button delete" onClick={() => handleDelete(transaction)}>
                  <img src="https://img.icons8.com/?size=100&id=102350&format=png&color=000000" alt="Delete" />
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {showPopup && (
        <div className="popup-overlay">
          <div className="popup-content">
            <h3>Edit Transaction</h3>
            <form className="popup-form">
              <div className="popup-field">
                <label>Amount</label>
                <input
                  name="amount"
                  value={currentTransaction.amount}
                  onChange={handleInputChange}
                  placeholder="Amount"
                />
              </div>
              <div className="popup-field">
                <label>Date</label>
                <input
                  name="date"
                  type="date"
                  value={currentTransaction.date}
                  onChange={handleInputChange}
                  onFocus={(e) => e.target.showPicker()}
                />
              </div>
              <div className="popup-field">
                <label>Mode of Payment</label>
                <input
                  name="modeOfPayment"
                  value={currentTransaction.modeOfPayment}
                  onChange={handleInputChange}
                  placeholder="Mode of Payment"
                />
              </div>
              <div className="popup-field">
                <label>Remarks</label>
                <input
                  name="remarks"
                  value={currentTransaction.remarks}
                  onChange={handleInputChange}
                  placeholder="Remarks"
                />
              </div>
              <div className="popup-field">
                <label>Category</label>
                <select
                  name="category"
                  value={currentTransaction.category}
                  onChange={handleInputChange}
                >
                  <option value="">Select category</option>
                  <option value="shopping">Shopping</option>
                  <option value="electricity">Electricity</option>
                  <option value="EMI">EMI</option>
                  <option value="bills">Bills</option>
                  <option value="investment">Investment</option>
                  <option value="groceries">Groceries</option>
                  <option value="travel">Travel</option>
                  <option value="petrol">Petrol</option>
                  <option value="others">Others</option>
                </select>
              </div>
              <div className="popup-buttons">
                <button type="button" onClick={handleUpdate}>Update</button>
                <button type="button" onClick={() => setShowPopup(false)}>Cancel</button>
              </div>
            </form>
          </div>
        </div>
      )}

      {showDeletePopup && (
        <div className="popup-overlay">
          <div className="popup-content">
            <h3>Delete Transaction</h3>
            <p>Are you sure you want to delete this transaction?</p>
            <div className="popup-buttons">
              <button type="button" onClick={confirmDelete}>Delete</button>
              <button type="button" onClick={() => setShowDeletePopup(false)}>Cancel</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default function Expense() {
  const [showSuccess, setShowSuccess] = useState(false);
  const [successMessage, setSuccessMessage] = useState('');
  const [messageType, setMessageType] = useState('success');

  const handleSubmitSuccess = () => {
    setSuccessMessage('Data submitted successfully!');
    setMessageType('success');
    showSuccessMessage();
  };

  const handleUpdateSuccess = () => {
    setSuccessMessage('Details updated successfully!');
    setMessageType('success');
    showSuccessMessage();
  };

  const handleDeleteSuccess = () => {
    setSuccessMessage('Transaction deleted successfully!');
    setMessageType('delete');
    showSuccessMessage();
  };

  const showSuccessMessage = () => {
    setShowSuccess(true);
    setTimeout(() => setShowSuccess(false), 3000);
  };

  return (
    <div className="Expense-page">
      <h1>Expense Page</h1>
      <div className={`message ${messageType} ${showSuccess ? 'show' : ''}`}>
        {successMessage}
      </div>
      <div className="form-container">
        <Form onSubmitSuccess={handleSubmitSuccess} />
      </div>
      <ExpenseTable onUpdateSuccess={handleUpdateSuccess} onDeleteSuccess={handleDeleteSuccess} />
    </div>
  );
}