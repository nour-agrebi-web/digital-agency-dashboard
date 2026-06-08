/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  darkMode: 'class',
  theme: {
    extend: {
      colors: {
        primary: '#6366F1',
        secondary: '#8B5CF6',
        success: '#10B981',
        warning: '#F59E0B',
        danger: '#EF4444',
        'dark-bg': '#0F172A',
        'light-bg': '#F8FAFC',
      },
    },
  },
  plugins: [
    require('@tailwindcss/forms'),
  ],
}
