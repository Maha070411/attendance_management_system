import { useContext, useState, useEffect } from "react";
import { AuthContext } from "../context/AuthContext";
import { students } from "../data/mockData";

const FacultyDashboard = () => {
  const { user } = useContext(AuthContext);

  // We copy students into local state so faculty can edit
  const [studentData, setStudentData] = useState(students);

  const [selectedSubject, setSelectedSubject] = useState("");

  // when the user object becomes available, pick the first subject
  // when user becomes available, choose the first subject
  // eslint-disable-next-line react-hooks/set-state-in-effect
  useEffect(() => {
    if (user && user.subjects && user.subjects.length > 0) {
      setSelectedSubject(user.subjects[0]);
    }
  }, [user]);

  // Guard against uninitialized user (e.g. during login redirect)
  if (!user) {
    return <div>Loading...</div>;
  }

  // 🔹 Mark Attendance (Add Present)
  const markPresent = (studentId) => {
    const updated = studentData.map((stu) => {
      if (stu.id === studentId) {
        const updatedSubjects = stu.subjects.map((sub) => {
          if (sub.name === selectedSubject) {
            return { ...sub, attended: sub.attended + 1, total: sub.total + 1 };
          }
          return sub;
        });
        return { ...stu, subjects: updatedSubjects };
      }
      return stu;
    });

    setStudentData(updated);
  };

  // 🔹 Mark Absent (Only increase total)
  const markAbsent = (studentId) => {
    const updated = studentData.map((stu) => {
      if (stu.id === studentId) {
        const updatedSubjects = stu.subjects.map((sub) => {
          if (sub.name === selectedSubject) {
            return { ...sub, total: sub.total + 1 };
          }
          return sub;
        });
        return { ...stu, subjects: updatedSubjects };
      }
      return stu;
    });

    setStudentData(updated);
  };

  // 🔹 Delete Student Record for Subject
  const deleteRecord = (studentId) => {
    const updated = studentData.map((stu) => {
      if (stu.id === studentId) {
        const filteredSubjects = stu.subjects.filter(
          (sub) => sub.name !== selectedSubject
        );
        return { ...stu, subjects: filteredSubjects };
      }
      return stu;
    });

    setStudentData(updated);
  };

  // 🔹 Sorting
  const sortHighToLow = () => {
    const sorted = [...studentData].sort((a, b) => {
      const subA = a.subjects.find((s) => s.name === selectedSubject);
      const subB = b.subjects.find((s) => s.name === selectedSubject);

      const perA = subA ? subA.attended / subA.total : 0;
      const perB = subB ? subB.attended / subB.total : 0;

      return perB - perA;
    });

    setStudentData(sorted);
  };

  const sortLowToHigh = () => {
    const sorted = [...studentData].sort((a, b) => {
      const subA = a.subjects.find((s) => s.name === selectedSubject);
      const subB = b.subjects.find((s) => s.name === selectedSubject);

      const perA = subA ? subA.attended / subA.total : 0;
      const perB = subB ? subB.attended / subB.total : 0;

      return perA - perB;
    });

    setStudentData(sorted);
  };

  return (
    <div className="container">
      <h2>{user.name}</h2>
      <p>
        {user.id} | {user.dept}
      </p>

      {/* 🔹 Subject Selection */}
      <h3>Select Subject</h3>
      <select
        value={selectedSubject}
        onChange={(e) => setSelectedSubject(e.target.value)}
      >
        {user.subjects.map((sub, index) => (
          <option key={index} value={sub}>
            {sub}
          </option>
        ))}
      </select>

      {/* 🔹 Sorting Buttons */}
      <div style={{ margin: "15px" }}>
        <button onClick={sortHighToLow}>High → Low</button>
        <button onClick={sortLowToHigh}>Low → High</button>
      </div>

      {/* 🔹 Attendance Table */}
      <table>
        <thead>
          <tr>
            <th>Name</th>
            <th>Roll</th>
            <th>Attended</th>
            <th>Total</th>
            <th>Percentage</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {studentData.map((stu) => {
            const subject = stu.subjects.find(
              (s) => s.name === selectedSubject
            );

            if (!subject) return null;

            const percentage = subject.total
              ? ((subject.attended / subject.total) * 100).toFixed(1)
              : "0.0";

            return (
              <tr key={stu.id}>
                <td>{stu.name}</td>
                <td>{stu.roll}</td>
                <td>{subject.attended}</td>
                <td>{subject.total}</td>
                <td>{percentage}%</td>
                <td>
                  <button onClick={() => markPresent(stu.id)}>Present</button>
                  <button onClick={() => markAbsent(stu.id)}>Absent</button>
                  <button onClick={() => deleteRecord(stu.id)}>Delete</button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
};

export default FacultyDashboard;