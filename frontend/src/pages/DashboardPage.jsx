import React from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../hooks/useAuth';
import toast from 'react-hot-toast';

export const DashboardPage = () => {
  const navigate = useNavigate();
  const { user, logout } = useAuth();

  const handleLogout = () => {
    logout();
    toast.success('Logged out successfully');
    navigate('/login');
  };

  const menuItems = [
    { label: '📊 Dashboard', href: '/dashboard' },
    { label: '👥 Clients', href: '/clients' },
    { label: '📁 Projects', href: '/projects' },
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 dark:from-gray-900 dark:to-gray-800">
      {/* Top Navigation */}
      <nav className="bg-white dark:bg-gray-900 shadow-sm border-b border-gray-200 dark:border-gray-700">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <h1 className="text-2xl font-bold text-indigo-600">Digital Agency</h1>
            <div className="flex items-center gap-6">
              <span className="text-gray-700 dark:text-gray-300 text-sm">
                Welcome, <strong>{user?.firstName}</strong>
              </span>
              <button
                onClick={handleLogout}
                className="px-4 py-2 bg-red-600 hover:bg-red-700 text-white rounded-lg transition"
              >
                Logout
              </button>
            </div>
          </div>
        </div>
      </nav>

      {/* Main Content */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        {/* Menu Cards */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-12">
          {menuItems.map((item) => (
            <Link
              key={item.href}
              to={item.href}
              className="bg-white dark:bg-gray-800 rounded-lg shadow-lg p-8 hover:shadow-xl transition transform hover:-translate-y-1"
            >
              <div className="text-4xl mb-4">{item.label.split(' ')[0]}</div>
              <h3 className="text-xl font-bold text-gray-900 dark:text-white">
                {item.label.split(' ')[1]}
              </h3>
              <p className="text-gray-500 dark:text-gray-400 mt-2">Manage your {item.label.split(' ')[1].toLowerCase()}</p>
            </Link>
          ))}
        </div>

        {/* User Info Card */}
        <div className="bg-white dark:bg-gray-800 rounded-lg shadow-lg p-8">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">Your Profile</h2>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="bg-indigo-50 dark:bg-indigo-900 p-6 rounded-lg">
              <h3 className="font-semibold text-indigo-900 dark:text-indigo-100 mb-4">Account Details</h3>
              <div className="space-y-2 text-indigo-800 dark:text-indigo-200 text-sm">
                <p><span className="font-medium">Name:</span> {user?.firstName} {user?.lastName}</p>
                <p><span className="font-medium">Email:</span> {user?.email}</p>
                <p><span className="font-medium">Role:</span> {user?.role}</p>
                {user?.department && <p><span className="font-medium">Department:</span> {user?.department}</p>}
              </div>
            </div>

            <div className="bg-purple-50 dark:bg-purple-900 p-6 rounded-lg">
              <h3 className="font-semibold text-purple-900 dark:text-purple-100 mb-4">Quick Start</h3>
              <ul className="space-y-2 text-purple-800 dark:text-purple-200 text-sm">
                <li>✨ Phase 3: Core Data Models Complete</li>
                <li>📊 Clients Management Available</li>
                <li>📁 Projects Management Available</li>
                <li>🔄 React Query Data Caching Enabled</li>
              </ul>
            </div>
          </div>

          {/* Features Coming Soon */}
          <div className="mt-8 p-6 bg-blue-50 dark:bg-blue-900 border border-blue-200 dark:border-blue-700 rounded-lg">
            <h3 className="text-lg font-semibold text-blue-900 dark:text-blue-100 mb-4">
              🚀 Features Coming Next
            </h3>
            <div className="grid grid-cols-2 md:grid-cols-4 gap-4 text-sm text-blue-800 dark:text-blue-200">
              <div>⏱️ Time Tracking</div>
              <div>💰 Invoicing</div>
              <div>✅ Task Management</div>
              <div>💬 Team Chat</div>
              <div>🔔 Notifications</div>
              <div>📋 Approvals</div>
              <div>📊 Analytics</div>
              <div>📈 Reports</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
