import { useNavigate } from "react-router-dom";

function StudentLogin() {
  const navigate = useNavigate();

  const handleLogin = () => {
    navigate("/student-dashboard");
  };

  return (
    <div>
      <h2>Student Login</h2>
      <input placeholder="Student ID" />
      <input type="password" placeholder="Password" />
      <button onClick={handleLogin}>Login</button>
    </div>
  );
}

export default StudentLogin;