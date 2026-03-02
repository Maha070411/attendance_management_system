import { useNavigate } from "react-router-dom";

function FacultyLogin() {
  const navigate = useNavigate();

  const handleLogin = () => {
    navigate("/faculty-dashboard");
  };

  return (
    <div>
      <h2>Faculty Login</h2>
      <input placeholder="Email" />
      <input type="password" placeholder="Password" />
      <button onClick={handleLogin}>Login</button>
    </div>
  );
}

export default FacultyLogin;