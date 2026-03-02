import { useContext, useState } from "react";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";

const StudentLogin = () => {
  const { loginStudent } = useContext(AuthContext);
  const navigate = useNavigate();

  const [id, setId] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = () => {
    const success = loginStudent(id, password);
    if (success) navigate("/student");
    else alert("Invalid credentials");
  };

  return (
    <div>
      <input placeholder="Student ID" onChange={(e) => setId(e.target.value)} />
      <input type="password" placeholder="Password"
        onChange={(e) => setPassword(e.target.value)} />
      <button onClick={handleLogin}>Login</button>
    </div>
  );
};

export default StudentLogin;