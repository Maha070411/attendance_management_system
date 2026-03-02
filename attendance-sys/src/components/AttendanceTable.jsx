const AttendanceTable = ({ subjects }) => {

  const getColor = (percentage) => {
    if (percentage >= 75) return "green";
    if (percentage >= 60) return "yellow";
    return "red";
  };

  return (
    <table>
      <thead>
        <tr>
          <th>Subject</th>
          <th>Total</th>
          <th>Attended</th>
          <th>Percentage</th>
        </tr>
      </thead>
      <tbody>
        {subjects.map((sub, index) => {
          const percentage = ((sub.attended / sub.total) * 100).toFixed(1);
          return (
            <tr key={index} className={getColor(percentage)}>
              <td>{sub.name}</td>
              <td>{sub.total}</td>
              <td>{sub.attended}</td>
              <td>{percentage}%</td>
            </tr>
          );
        })}
      </tbody>
    </table>
  );
};

export default AttendanceTable;