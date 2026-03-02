import { useContext, useState } from "react";
import { AuthContext } from "../context/AuthContext";
import AttendanceTable from "../components/AttendanceTable";
import SortControls from "../components/SortControls";

const StudentDashboard = () => {
  const { user } = useContext(AuthContext);
  const [subjects, setSubjects] = useState(user.subjects);

  const sortHighToLow = () => {
    const sorted = [...subjects].sort(
      (a, b) => (b.attended / b.total) - (a.attended / a.total)
    );
    setSubjects(sorted);
  };

  const sortLowToHigh = () => {
    const sorted = [...subjects].sort(
      (a, b) => (a.attended / a.total) - (b.attended / b.total)
    );
    setSubjects(sorted);
  };

  return (
    <div className="container">
      <h2>{user.name}</h2>
      <p>{user.roll} | {user.dept} | {user.year}</p>

      <SortControls
        highToLow={sortHighToLow}
        lowToHigh={sortLowToHigh}
      />

      <AttendanceTable subjects={subjects} />
    </div>
  );
};

export default StudentDashboard;