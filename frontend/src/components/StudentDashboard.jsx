import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService, attendanceService } from '../services/api';
import { LogOut, Calendar, Clock, Activity, CheckCircle, XCircle, AlertCircle, Inbox } from 'lucide-react';

export default function StudentDashboard() {
    const [history, setHistory] = useState([]);
    const [user, setUser] = useState(null);
    const [isLoading, setIsLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        const currentUser = authService.getCurrentUser();
        if (!currentUser || currentUser.role !== 'STUDENT') {
            navigate('/');
            return;
        }
        setUser(currentUser);
        fetchHistory(currentUser.id);
    }, [navigate]);

    const fetchHistory = async (studentId) => {
        try {
            const response = await attendanceService.getStudentHistory(studentId);
            setHistory(response.data);
        } catch (error) {
            console.error("Failed to fetch history");
        } finally {
            setIsLoading(false);
        }
    };

    const handleLogout = () => {
        authService.logout();
        navigate('/');
    };

    const getStatusIcon = (status) => {
        switch (status) {
            case 'PRESENT': return <CheckCircle size={16} className="text-emerald-400" />;
            case 'LATE': return <XCircle size={16} className="text-red-400" />;
            case 'HALF_DAY': return <AlertCircle size={16} className="text-amber-400" />;
            default: return null;
        }
    };

    const calculateTotalHoursWeek = () => {
        if (history.length === 0) return 0;
        // Simple mock calculation for layout demonstration
        const sum = history.slice(0, 5).reduce((acc, curr) => acc + (curr.totalHours || 0), 0);
        return sum.toFixed(1);
    };

    if (!user || isLoading) return <div className="login-container"><div className="login-logo"><div className="login-icon"><Calendar className="animate-pulse" size={32} /></div></div></div>;

    return (
        <div className="dashboard-container">
            <header className="dashboard-header">
                <div className="header-profile">
                    <div className="avatar">
                        {user.name.charAt(0)}
                    </div>
                    <div className="header-info">
                        <h1>{user.name}</h1>
                        <p>ID: {user.id} | {user.email}</p>
                    </div>
                </div>
                <button onClick={handleLogout} className="logout-button">
                    <LogOut size={18} />
                    <span>Sign Out</span>
                </button>
            </header>

            <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
                <div className="dashboard-content" style={{ padding: '24px' }}>
                    <div className="flex items-center gap-4">
                        <div className="p-3 rounded-xl bg-indigo-500/10 text-indigo-400">
                            <Calendar size={24} />
                        </div>
                        <div>
                            <p className="text-slate-400 text-sm font-medium m-0">Total Classes</p>
                            <h3 className="text-2xl font-bold m-0 mt-1">{history.length}</h3>
                        </div>
                    </div>
                </div>

                <div className="dashboard-content" style={{ padding: '24px' }}>
                    <div className="flex items-center gap-4">
                        <div className="p-3 rounded-xl bg-emerald-500/10 text-emerald-400">
                            <CheckCircle size={24} />
                        </div>
                        <div>
                            <p className="text-slate-400 text-sm font-medium m-0">Days Present</p>
                            <h3 className="text-2xl font-bold m-0 mt-1">
                                {history.filter(h => h.status === 'PRESENT').length}
                            </h3>
                        </div>
                    </div>
                </div>

                <div className="dashboard-content" style={{ padding: '24px' }}>
                    <div className="flex items-center gap-4">
                        <div className="p-3 rounded-xl bg-amber-500/10 text-amber-400">
                            <Clock size={24} />
                        </div>
                        <div>
                            <p className="text-slate-400 text-sm font-medium m-0">Recent Hours</p>
                            <h3 className="text-2xl font-bold m-0 mt-1">{calculateTotalHoursWeek()}h</h3>
                        </div>
                    </div>
                </div>
            </div>

            <div className="dashboard-content">
                <div className="content-header">
                    <Activity className="content-icon" size={24} />
                    <h2>Attendance Log</h2>
                </div>

                {history.length === 0 ? (
                    <div className="empty-state">
                        <Inbox size={48} className="empty-icon text-slate-500 mx-auto" />
                        <h3 className="text-xl mt-4 mb-2">No Records Found</h3>
                        <p>Your attendance log is currently empty. Check back after your next class.</p>
                    </div>
                ) : (
                    <div className="table-container">
                        <table className="attendance-table">
                            <thead>
                                <tr>
                                    <th>Date</th>
                                    <th>Sign In</th>
                                    <th>Sign Out</th>
                                    <th>Duration</th>
                                    <th>Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                {history.map((record, index) => (
                                    <tr key={index}>
                                        <td className="font-medium text-slate-200">
                                            {new Date(record.date).toLocaleDateString('en-US', { weekday: 'short', month: 'short', day: 'numeric' })}
                                        </td>
                                        <td>{record.checkInTime || '-'}</td>
                                        <td>{record.checkOutTime || '-'}</td>
                                        <td className="text-slate-300 font-medium">{record.totalHours ? `${record.totalHours} hrs` : '-'}</td>
                                        <td>
                                            <span className={`status-badge status-${record.status.toLowerCase()} gap-2`}>
                                                {getStatusIcon(record.status)}
                                                {record.status.replace('_', ' ')}
                                            </span>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                )}
            </div>
        </div>
    );
}
