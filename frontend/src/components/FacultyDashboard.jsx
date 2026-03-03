import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService, attendanceService } from '../services/api';
import { LogOut, CheckCircle, AlertTriangle, PenTool, User as UserIcon, Calendar, Clock, Loader } from 'lucide-react';

export default function FacultyDashboard() {
    const [studentId, setStudentId] = useState('');
    const [date, setDate] = useState('');
    const [checkInTime, setCheckInTime] = useState('');
    const [checkOutTime, setCheckOutTime] = useState('');
    const [message, setMessage] = useState({ type: '', text: '' });
    const [user, setUser] = useState(null);
    const [isSubmitting, setIsSubmitting] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const currentUser = authService.getCurrentUser();
        if (!currentUser || currentUser.role !== 'FACULTY') {
            navigate('/');
            return;
        }
        setUser(currentUser);
        setDate(new Date().toISOString().split('T')[0]);
    }, [navigate]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsSubmitting(true);
        setMessage({ type: '', text: '' });

        try {
            await attendanceService.markAttendance(
                studentId,
                date,
                checkInTime || null,
                checkOutTime || null
            );
            setMessage({ type: 'success', text: 'Attendance logged successfully.' });
            setStudentId('');
            setCheckInTime('');
            setCheckOutTime('');
        } catch (error) {
            setMessage({
                type: 'error',
                text: error.response?.data || "Unable to process. Ensure valid DB ID and verify duplication constraints."
            });
        } finally {
            setIsSubmitting(false);
        }
    };

    const handleLogout = () => {
        authService.logout();
        navigate('/');
    };

    if (!user) return <div className="login-container"><Loader className="animate-spin" size={32} /></div>;

    return (
        <div className="dashboard-container">
            <header className="dashboard-header">
                <div className="header-profile">
                    <div className="avatar bg-gradient-to-br from-indigo-500 to-purple-600">
                        {user.name.charAt(0)}
                    </div>
                    <div className="header-info">
                        <h1>Faculty Portal</h1>
                        <p>Welcome, Professor {user.name.split(' ')[0]}</p>
                    </div>
                </div>
                <button onClick={handleLogout} className="logout-button">
                    <LogOut size={18} />
                    <span>Sign Out</span>
                </button>
            </header>

            <div className="dashboard-content max-w-3xl mx-auto">
                <div className="content-header">
                    <PenTool className="content-icon" size={24} />
                    <h2>Mark Student Attendance</h2>
                </div>

                {message.text && (
                    <div className={message.type === 'error' ? 'error-message' : 'success-message'}>
                        {message.type === 'error' ? <AlertTriangle size={20} /> : <CheckCircle size={20} />}
                        {message.text}
                    </div>
                )}

                <form onSubmit={handleSubmit} className="mt-8">
                    <div className="form-grid text-left">
                        <div className="form-group mb-0">
                            <label>Student Database ID</label>
                            <div className="input-wrapper">
                                <UserIcon size={18} className="input-icon" />
                                <input
                                    type="number"
                                    value={studentId}
                                    onChange={(e) => setStudentId(e.target.value)}
                                    required
                                    placeholder="e.g. 1"
                                    min="1"
                                    className="with-icon"
                                />
                            </div>
                        </div>
                        <div className="form-group mb-0">
                            <label>Record Date</label>
                            <div className="input-wrapper">
                                <Calendar size={18} className="input-icon" />
                                <input
                                    type="date"
                                    value={date}
                                    onChange={(e) => setDate(e.target.value)}
                                    required
                                    className="with-icon"
                                />
                            </div>
                        </div>
                    </div>

                    <div className="form-grid text-left">
                        <div className="form-group mb-0">
                            <label>Sign In Time</label>
                            <div className="input-wrapper">
                                <Clock size={18} className="input-icon" />
                                <input
                                    type="time"
                                    value={checkInTime}
                                    onChange={(e) => setCheckInTime(e.target.value)}
                                    required
                                    className="with-icon"
                                />
                            </div>
                        </div>
                        <div className="form-group mb-0">
                            <label>Sign Out Time</label>
                            <div className="input-wrapper">
                                <Clock size={18} className="input-icon" />
                                <input
                                    type="time"
                                    value={checkOutTime}
                                    onChange={(e) => setCheckOutTime(e.target.value)}
                                    className="with-icon"
                                />
                            </div>
                        </div>
                    </div>

                    <div className="mt-8 pt-6 border-t border-slate-700/50">
                        <button type="submit" className="login-button py-4 w-full text-lg" disabled={isSubmitting}>
                            {isSubmitting ? (
                                <>
                                    <Loader className="animate-spin" size={20} />
                                    <span>Processing...</span>
                                </>
                            ) : (
                                <>
                                    <CheckCircle size={20} />
                                    <span>Submit Attendance Record</span>
                                </>
                            )}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}
