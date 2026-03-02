/* eslint-disable react-refresh/only-export-components */
import { createContext, useState } from "react";
import { students, faculty } from "../data/mockData";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  const loginStudent = (id, password) => {
    const found = students.find(
      (s) => s.id === id && s.password === password
    );
    if (found) {
      setUser({ ...found, role: "student" });
      return true;
    }
    return false;
  };

  const loginFaculty = (email, password) => {
    const found = faculty.find(
      (f) => f.email === email && f.password === password
    );
    if (found) {
      setUser({ ...found, role: "faculty" });
      return true;
    }
    return false;
  };

  const logout = () => setUser(null);

  return (
    <AuthContext.Provider value={{ user, loginStudent, loginFaculty, logout }}>
      {children}
    </AuthContext.Provider>
  );
};