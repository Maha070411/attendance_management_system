import { useState } from "react";
import StudentLogin from "./StudentLogin";
import FacultyLogin from "./FacultyLogin";

const LoginTabs = () => {
  const [active, setActive] = useState("student");

  return (
    <div>
      <div className="tabs">
        <button onClick={() => setActive("student")}>Student</button>
        <button onClick={() => setActive("faculty")}>Faculty</button>
      </div>

      {active === "student" ? <StudentLogin /> : <FacultyLogin />}
    </div>
  );
};

export default LoginTabs;