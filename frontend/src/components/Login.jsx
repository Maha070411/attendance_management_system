import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/api';
import { LogIn, User, Lock, BookOpen, Eye, EyeOff } from 'lucide-react';

export default function Login() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [role, setRole] = useState('STUDENT');
    const [error, setError] = useState('');
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setIsLoading(true);
        setError('');
        try {
            const response = await authService.login(email, password, role);
            if (response.data.token) {
                localStorage.setItem('token', response.data.token);
                localStorage.setItem('user', JSON.stringify(response.data));

                if (role === 'STUDENT') {
                    navigate('/student-dashboard');
                } else {
                    navigate('/faculty-dashboard');
                }
            }
        } catch (err) {
            setError(err.response?.data || "Invalid credentials. Please try again.");
            setIsLoading(false);
        }
    };

    return (
        <div className="login-container">
            <div className="login-card">
                <div className="login-header">
                    <div className="login-logo">
                        <div className="login-icon">
                            <BookOpen size={32} />
                        </div>
                    </div>
                    <h2>Welcome Back</h2>
                    <p>Sign in to the Attendance System</p>
                </div>

                {error && (
                    <div className="error-message">
                        <User size={18} />
                        {error}
                    </div>
                )}

                <form onSubmit={handleLogin}>
                    <div className="form-group">
                        <label>Login As</label>
                        <div className="input-wrapper">
                            <User size={18} className="input-icon" />
                            <select
                                value={role}
                                onChange={(e) => setRole(e.target.value)}
                                className="with-icon"
                            >
                                <option value="STUDENT">Student Portal</option>
                                <option value="FACULTY">Faculty Portal</option>
                            </select>
                        </div>
                    </div>
                    <div className="form-group">
                        <label>Email Address</label>
                        <div className="input-wrapper">
                            <User size={18} className="input-icon" />
                            <input
                                type="email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                                placeholder="professor@university.edu"
                                className="with-icon"
                            />
                        </div>
                    </div>
                    <div className="form-group">
                        <label>Password</label>
                        <div className="input-wrapper">
                            <Lock size={18} className="input-icon" />
                            <input
                                type={showPassword ? "text" : "password"}
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                                placeholder="••••••••"
                                className="with-icon"
                            />
                            <button
                                type="button"
                                className="password-toggle"
                                onClick={() => setShowPassword(!showPassword)}
                            >
                                {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
                            </button>
                        </div>
                    </div>
                    <button type="submit" className="login-button" disabled={isLoading}>
                        {isLoading ? 'Signing In...' : (
                            <>
                                <span>Sign In Securely</span>
                                <LogIn size={20} />
                            </>
                        )}
                    </button>
                </form>
            </div>
        </div>
    );
}
