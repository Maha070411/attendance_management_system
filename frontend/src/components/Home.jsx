import { Link } from "react-router-dom";

function Home() {
  return (
    <div>
      <h1>Attendance Management System</h1>

      <Link to="/student-login">
        <button>Student Login</button>
      </Link>

      <Link to="/faculty-login">
        <button>Faculty Login</button>
      </Link>
    </div>
  );
}

export default Home;