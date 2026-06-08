import React from 'react';
import { useNavigate } from 'react-router-dom';
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

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 dark:from-gray-900 dark:to-gray-800">
      {/* Top Navigation */}
      <nav className="bg-white dark:bg-gray-900 shadow-sm border-b border-gray-200 dark:border-gray-700">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <h1 className="text-2xl font-bold text-indigo-600">Digital Agency</h1>
            <div className="flex items-center gap-4">
              <span className="text-gray-700 dark:text-gray-300">
                Welcome, <strong>{user?.firstName} {user?.lastName}</strong>
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
        <div className="bg-white dark:bg-gray-900 rounded-lg shadow-lg p-8">
          <h2 className="text-3xl font-bold text-gray-900 dark:text-white mb-4">
            Welcome to Dashboard!
          </h2>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mt-8">
            {/* User Info Card */}
            <div className="bg-gradient-to-br from-indigo-50 to-indigo-100 dark:from-indigo-900 dark:to-indigo-800 p-6 rounded-lg">
              <h3 className="text-lg font-semibold text-indigo-900 dark:text-indigo-100 mb-4">
                Your Profile
              </h3>
              <div className="space-y-2 text-indigo-800 dark:text-indigo-200">
                <p><strong>Email:</strong> {user?.email}</p>
                <p><strong>Name:</strong> {user?.firstName} {user?.lastName}</p>
                <p><strong>Role:</strong> {user?.role}</p>
                {user?.department && <p><strong>Department:</strong> {user?.department}</p>}
              </div>
            </div>

            {/* Quick Stats Card */}
            <div className="bg-gradient-to-br from-purple-50 to-purple-100 dark:from-purple-900 dark:to-purple-800 p-6 rounded-lg">
              <h3 className="text-lg font-semibold text-purple-900 dark:text-purple-100 mb-4">
                Quick Start
              </h3>
              <ul className="space-y-2 text-purple-800 dark:text-purple-200">
                <li>✨ Phase 2: Authentication Complete</li>
                <li>📅 Next: Core Data Models & CRUD</li>
                <li>🚀 Building Full-Stack Features</li>
                <li>🎯 TIER 1 & 2 Implementation</li>
              </ul>
            </div>
          </div>

          {/* Features Coming Soon */}
          <div className="mt-8 p-6 bg-blue-50 dark:bg-blue-900 border border-blue-200 dark:border-blue-700 rounded-lg">
            <h3 className="text-lg font-semibold text-blue-900 dark:text-blue-100 mb-4">
              🔧 Features Coming in Next Phases
            </h3>
            <div className="grid grid-cols-2 md:grid-cols-3 gap-4">
              <div>📊 Dashboard Analytics</div>
              <div>👥 Client Management</div>
              <div>📁 Project Tracking</div>
              <div>⏱️ Time Tracking</div>
              <div>💰 Invoicing</div>
              <div>💬 Team Chat</div>
              <div>🔔 Real-time Notifications</div>
              <div>✅ Approvals Workflow</div>
              <div>📈 Advanced Analytics</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
