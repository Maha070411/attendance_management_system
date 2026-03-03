import axios from 'axios';

const API_URL = 'http://localhost:8082/api';

const api = axios.create({
    baseURL: API_URL,
});

api.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export const authService = {
    login: (email, password, role) => {
        return api.post('/auth/login', { email, password, role });
    },
    logout: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    },
    getCurrentUser: () => {
        return JSON.parse(localStorage.getItem('user'));
    }
};

export const attendanceService = {
    markAttendance: (studentId, date, checkInTime, checkOutTime) => {
        return api.post('/attendance/mark', { studentId, date, checkInTime, checkOutTime });
    },
    getStudentHistory: (studentId) => {
        return api.get(`/attendance/history/student/${studentId}`);
    }
};

export default api;
