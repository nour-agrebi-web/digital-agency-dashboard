import React, { useState } from 'react';
import { useProjects, useDeleteProject } from '../hooks/useQueries';
import { useClients } from '../hooks/useQueries';
import ProjectModal from '../components/ProjectModal';

export const ProjectsPage = () => {
  const { data: projects = [], isLoading: projectsLoading } = useProjects();
  const { data: clients = [] } = useClients();
  const deleteMutation = useDeleteProject();

  const [showModal, setShowModal] = useState(false);
  const [editingProject, setEditingProject] = useState(null);
  const [filterStatus, setFilterStatus] = useState('');

  const filteredProjects = projects.filter(p =>
    !filterStatus || p.status === filterStatus
  );

  const getClientName = (clientId) => {
    return clients.find(c => c.id === clientId)?.name || 'Unknown';
  };

  const getStatusBadgeColor = (status) => {
    const colors = {
      PLANNING: 'bg-blue-100 text-blue-800',
      ACTIVE: 'bg-green-100 text-green-800',
      COMPLETED: 'bg-purple-100 text-purple-800',
      ON_HOLD: 'bg-yellow-100 text-yellow-800',
    };
    return colors[status] || 'bg-gray-100 text-gray-800';
  };

  const handleDelete = (id) => {
    if (window.confirm('Are you sure?')) {
      deleteMutation.mutate(id);
    }
  };

  if (projectsLoading) {
    return <div className="flex items-center justify-center min-h-screen"><div className="animate-spin rounded-full h-12 w-12 border-b-2 border-indigo-600"></div></div>;
  }

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-900">
      {/* Header */}
      <div className="bg-white dark:bg-gray-800 shadow-sm border-b border-gray-200 dark:border-gray-700">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <div className="flex justify-between items-center">
            <h1 className="text-3xl font-bold text-gray-900 dark:text-white">Projects</h1>
            <button
              onClick={() => {
                setEditingProject(null);
                setShowModal(true);
              }}
              className="px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg transition"
            >
              + New Project
            </button>
          </div>
        </div>
      </div>

      {/* Content */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Filter */}
        <div className="mb-6 flex gap-2">
          <button
            onClick={() => setFilterStatus('')}
            className={`px-4 py-2 rounded-lg transition ${!filterStatus ? 'bg-indigo-600 text-white' : 'bg-white dark:bg-gray-800 text-gray-700 dark:text-gray-300'}`}
          >
            All
          </button>
          {['PLANNING', 'ACTIVE', 'COMPLETED', 'ON_HOLD'].map(status => (
            <button
              key={status}
              onClick={() => setFilterStatus(status)}
              className={`px-4 py-2 rounded-lg transition ${filterStatus === status ? 'bg-indigo-600 text-white' : 'bg-white dark:bg-gray-800 text-gray-700 dark:text-gray-300'}`}
            >
              {status}
            </button>
          ))}
        </div>

        {/* Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {filteredProjects.map(project => (
            <div key={project.id} className="bg-white dark:bg-gray-800 rounded-lg shadow-md p-6 hover:shadow-lg transition">
              <div className="flex justify-between items-start mb-4">
                <h3 className="text-lg font-bold text-gray-900 dark:text-white">{project.name}</h3>
                <span className={`px-3 py-1 rounded-full text-xs font-semibold ${getStatusBadgeColor(project.status)}`}>
                  {project.status}
                </span>
              </div>

              <p className="text-sm text-gray-500 dark:text-gray-400 mb-4">{getClientName(project.client.id)}</p>

              <div className="space-y-2 mb-4 text-sm">
                <div><span className="font-medium">Budget:</span> ${project.budget?.toFixed(2) || '0.00'}</div>
                <div><span className="font-medium">Used:</span> ${project.actualCost?.toFixed(2) || '0.00'}</div>
                <div><span className="font-medium">Remaining:</span> ${project.remainingBudget?.toFixed(2) || '0.00'}</div>
              </div>

              <div className="flex gap-2 pt-4 border-t border-gray-200 dark:border-gray-700">
                <button onClick={() => {
                  setEditingProject(project);
                  setShowModal(true);
                }} className="flex-1 text-indigo-600 hover:text-indigo-700 text-sm font-medium">Edit</button>
                <button onClick={() => handleDelete(project.id)} className="flex-1 text-red-600 hover:text-red-700 text-sm font-medium">Delete</button>
              </div>
            </div>
          ))}
        </div>

        {filteredProjects.length === 0 && (
          <div className="text-center py-12">
            <p className="text-gray-500 dark:text-gray-400">No projects found</p>
          </div>
        )}
      </div>

      {showModal && (
        <ProjectModal
          project={editingProject}
          clients={clients}
          onClose={() => {
            setShowModal(false);
            setEditingProject(null);
          }}
        />
      )}
    </div>
  );
};
