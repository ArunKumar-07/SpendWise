import React, { useState, useEffect } from 'react';
import axios from '../axiosConfig'; 
import { Bar, Pie } from 'react-chartjs-2';
import "./Dashboard.css";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement
} from 'chart.js';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
  ArcElement
);

const Dashboard = () => {
  const [expenseData, setExpenseData] = useState(null);
  const [transactions, setTransactions] = useState([]);
  const [chartData, setChartData] = useState({
    barData: null,
    pieData: null
  });
  const [selectedMonth, setSelectedMonth] = useState(new Date().getMonth());
  const [selectedYear, setSelectedYear] = useState(new Date().getFullYear());

  useEffect(() => {
    fetchExpenseData();
    fetchAllTransactions();
  }, []);

  useEffect(() => {
    if (transactions.length > 0) {
      console.log('Transactions:', transactions);
      prepareChartData(transactions);
    }
  }, [transactions, selectedMonth, selectedYear]);

  const fetchExpenseData = async () => {
    try {
      const response = await axios.get('http://localhost:8080/new/get/expense');
      setExpenseData(response.data);
    } catch (error) {
      console.error('Error fetching expense data:', error);
    }
  };

  const fetchAllTransactions = async () => {
    try {
      const response = await axios.get('http://localhost:8080/new/all');
      if (response.data && Array.isArray(response.data)) {
        setTransactions(response.data);
      } else {
        console.error('Unexpected data format:', response.data);
      }
    } catch (error) {
      console.error('Error fetching transactions:', error);
    }
  };

  const prepareChartData = (data) => {
    const monthlyExpenses = {};
    const categoryExpenses = {};

    const validTransactions = data.filter(transaction => transaction && transaction.statement);

    validTransactions.forEach(transaction => {
      const date = new Date(transaction.date);
      const month = date.getMonth();
      const year = date.getFullYear();
      const amount = parseFloat(transaction.amount);
      const category = transaction.category ? transaction.category.toLowerCase() : 'uncategorized';

      if (transaction.statement && transaction.statement.toLowerCase() === 'expense') {
        // Monthly expenses for bar chart
        if (year === selectedYear) {
          if (!monthlyExpenses[month]) {
            monthlyExpenses[month] = 0;
          }
          monthlyExpenses[month] += amount;
        }

        // Category expenses for pie chart
        if (month === selectedMonth && year === selectedYear) {
          if (!categoryExpenses[category]) {
            categoryExpenses[category] = 0;
          }
          categoryExpenses[category] += amount;
        }
      }
    });

    const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

    setChartData({
      barData: {
        labels: months,
        datasets: [{
          label: `Monthly Spend (${selectedYear})`,
          data: months.map((_, index) => monthlyExpenses[index] || 0),
          backgroundColor: 'rgba(75, 192, 192, 0.6)',
        }]
      },
      pieData: {
        labels: Object.keys(categoryExpenses),
        datasets: [{
          data: Object.values(categoryExpenses),
          backgroundColor: [
            '#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40',
            '#FF8C00', '#008080', '#8B008B', '#556B2F', '#483D8B', '#2F4F4F'
          ],
        }]
      }
    });
  };

  const handleMonthChange = (event) => {
    setSelectedMonth(parseInt(event.target.value));
  };

  const handleYearChange = (event) => {
    setSelectedYear(parseInt(event.target.value));
  };

  return (
    <div className="dashboard">
      <div className="expense-summary">
        {expenseData && (
          <>
            <h2>Expense Summary</h2>
            {/* Add your expense summary content here */}
          </>
        )}
      </div>
      <div className="charts-container">
        <div className="chart-wrapper bar-chart">
          {chartData.barData && (
            <>
              <h3>Monthly Spend</h3>
              <select value={selectedYear} onChange={handleYearChange}>
                {[...Array(5)].map((_, i) => (
                  <option key={i} value={new Date().getFullYear() - i}>
                    {new Date().getFullYear() - i}
                  </option>
                ))}
              </select>
              <Bar 
                data={chartData.barData} 
                options={{
                  responsive: true,
                  maintainAspectRatio: false,
                  scales: {
                    y: {
                      beginAtZero: true,
                      title: {
                        display: true,
                        text: 'Amount (₹)'
                      }
                    }
                  }
                }} 
              />
            </>
          )}
        </div>
        <div className="chart-wrapper pie-chart">
          {chartData.pieData && (
            <>
              <h3>Expenses by Category</h3>
              <select value={selectedMonth} onChange={handleMonthChange}>
                {['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'].map((month, index) => (
                  <option key={index} value={index}>{month}</option>
                ))}
              </select>
              <Pie 
                data={chartData.pieData} 
                options={{
                  responsive: true,
                  maintainAspectRatio: false,
                  plugins: {
                    legend: {
                      position: 'right',
                    },
                    title: {
                      display: true,
                      text: `Expenses for ${['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][selectedMonth]} ${selectedYear}`
                    }
                  }
                }} 
              />
            </>
          )}
        </div>
      </div>
      <div className="lower-section">
        <h2 className="all-transactions-heading">All Transactions</h2>
        <table>
          <thead>
            <tr>
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
            {transactions.map((transaction, index) => (
              <tr key={index}>
                <td className={`amount ${transaction.statement && transaction.statement.toLowerCase() === 'income' ? 'income' : 'expense'}`}>
                  ₹{transaction.amount}
                </td>
                <td>{new Date(transaction.date).toLocaleDateString()}</td>
                <td>{transaction.modeOfPayment}</td>
                <td>{transaction.remarks}</td>
                <td>{transaction.category}</td>
                <td className="current-balance">₹{transaction.currentBalance}</td>
                <td>
                  {/* Add your action buttons here */}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Dashboard;