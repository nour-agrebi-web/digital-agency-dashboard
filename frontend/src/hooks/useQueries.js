import { useQuery, useMutation, useQueryClient } from 'react-query';
import api from '../services/api';
import toast from 'react-hot-toast';

// ========== CLIENTS ==========

export const useClients = () => {
  return useQuery('clients', async () => {
    const response = await api.get('/clients');
    return response.data;
  });
};

export const useClient = (id) => {
  return useQuery(['client', id], async () => {
    if (!id) return null;
    const response = await api.get(`/clients/${id}`);
    return response.data;
  });
};

export const useCreateClient = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async (data) => {
      const response = await api.post('/clients', data);
      return response.data;
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries('clients');
        toast.success('Client created successfully');
      },
      onError: (error) => {
        toast.error(error.response?.data?.message || 'Failed to create client');
      },
    }
  );
};

export const useUpdateClient = (id) => {
  const queryClient = useQueryClient();
  return useMutation(
    async (data) => {
      const response = await api.put(`/clients/${id}`, data);
      return response.data;
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['client', id]);
        queryClient.invalidateQueries('clients');
        toast.success('Client updated successfully');
      },
      onError: (error) => {
        toast.error(error.response?.data?.message || 'Failed to update client');
      },
    }
  );
};

export const useDeleteClient = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async (id) => {
      await api.delete(`/clients/${id}`);
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries('clients');
        toast.success('Client deleted successfully');
      },
      onError: (error) => {
        toast.error(error.response?.data?.message || 'Failed to delete client');
      },
    }
  );
};

// ========== PROJECTS ==========

export const useProjects = () => {
  return useQuery('projects', async () => {
    const response = await api.get('/projects');
    return response.data;
  });
};

export const useProject = (id) => {
  return useQuery(['project', id], async () => {
    if (!id) return null;
    const response = await api.get(`/projects/${id}`);
    return response.data;
  });
};

export const useProjectsByClient = (clientId) => {
  return useQuery(['projects', clientId], async () => {
    if (!clientId) return [];
    const response = await api.get(`/projects/client/${clientId}`);
    return response.data;
  });
};

export const useCreateProject = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async (data) => {
      const response = await api.post('/projects', data);
      return response.data;
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries('projects');
        toast.success('Project created successfully');
      },
      onError: (error) => {
        toast.error(error.response?.data?.message || 'Failed to create project');
      },
    }
  );
};

export const useUpdateProject = (id) => {
  const queryClient = useQueryClient();
  return useMutation(
    async (data) => {
      const response = await api.put(`/projects/${id}`, data);
      return response.data;
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['project', id]);
        queryClient.invalidateQueries('projects');
        toast.success('Project updated successfully');
      },
      onError: (error) => {
        toast.error(error.response?.data?.message || 'Failed to update project');
      },
    }
  );
};

export const useDeleteProject = () => {
  const queryClient = useQueryClient();
  return useMutation(
    async (id) => {
      await api.delete(`/projects/${id}`);
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries('projects');
        toast.success('Project deleted successfully');
      },
      onError: (error) => {
        toast.error(error.response?.data?.message || 'Failed to delete project');
      },
    }
  );
};
