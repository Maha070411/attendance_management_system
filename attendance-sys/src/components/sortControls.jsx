const SortControls = ({ highToLow, lowToHigh }) => {
  return (
    <div>
      <button onClick={highToLow}>High → Low</button>
      <button onClick={lowToHigh}>Low → High</button>
    </div>
  );
};

export default SortControls;