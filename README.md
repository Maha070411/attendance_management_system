# 📚 Attendance Management System – Use Case Explanation

## 🎯 Project Overview

The **Attendance Management System** is a full-stack web application designed to digitally manage and monitor student attendance. It provides secure authentication and role-based access for **Students** and **Faculty**, ensuring accurate tracking of check-in and check-out records.

The system replaces manual attendance registers with an automated, secure, and real-time solution.

---

## 👥 Primary Actors

- **Student**
- **Faculty**
- **System (Backend + Database)**

---

## 🔐 Use Case 1: User Authentication

### 🎭 Actors:
- Student  
- Faculty  

### 📌 Description:
Users must log in using their registered credentials to access the system.

### 🔄 Flow:
1. User enters email and password.  
2. System validates credentials.  
3. If valid → JWT token is generated.  
4. User is redirected to their respective dashboard.  
5. If invalid → Error message is shown.  

### 🎯 Outcome:
- Secure login  
- Role-based access control  

---

## 🎓 Use Case 2: Student Views Attendance

### 🎭 Actor:
- Student  

### 📌 Description:
Students can view their attendance logs and total hours attended.

### 🔄 Flow:
1. Student logs in.  
2. Dashboard displays:
   - Daily check-in time  
   - Daily check-out time  
   - Total hours attended  
   - Attendance summary  
3. Student can filter by date (if implemented).  

### 🎯 Outcome:
- Transparency in attendance records  
- Self-monitoring of attendance performance  

---

## ⏰ Use Case 3: Faculty Marks Check-In

### 🎭 Actor:
- Faculty  

### 📌 Description:
Faculty records the student’s check-in time.

### 🔄 Flow:
1. Faculty logs in.  
2. Enters Student ID.  
3. Clicks **"Check-In"**.  
4. System records current timestamp.  
5. Confirmation message displayed.  

### 🎯 Outcome:
- Accurate entry time stored in database  
- Prevents duplicate check-ins  

---

## ⏳ Use Case 4: Faculty Marks Check-Out

### 🎭 Actor:
- Faculty  

### 📌 Description:
Faculty records the student’s check-out time.

### 🔄 Flow:
1. Faculty enters Student ID.  
2. Clicks **"Check-Out"**.  
3. System updates attendance record with exit time.  
4. Total working hours calculated automatically.  

### 🎯 Outcome:
- Complete attendance record for the day  
- Automatic hour calculation  

---

## 📊 Use Case 5: Faculty Views Attendance Records

### 🎭 Actor:
- Faculty  

### 📌 Description:
Faculty can view attendance records by date or by student.

### 🔄 Flow:
1. Faculty selects date.  
2. System fetches all attendance entries for that date.  
3. Displays list with:
   - Student Name  
   - Check-In Time  
   - Check-Out Time  
   - Total Hours  

### 🎯 Outcome:
- Monitoring student discipline  
- Easy reporting  

---

## 🛡 Use Case 6: Secure API Access

### 🎭 Actor:
- System  

### 📌 Description:
All protected APIs require a valid JWT token.

### 🔄 Flow:
1. User sends request with JWT token.  
2. Backend validates token.  
3. If valid → API executes.  
4. If invalid → Access denied (401 Unauthorized).  

### 🎯 Outcome:
- Secure communication  
- Prevention of unauthorized access  

---

## 🗄 Database Interaction Use Case

### 📌 Description:

The system stores:

- User details (Student / Faculty)  
- Attendance logs  
- Date-wise records  

Each attendance record contains:

- Student ID  
- Date  
- Check-In Time  
- Check-Out Time  
- Total Hours  

---

## Features
- JWT based User Authentication
- Student Dashboard for tracking attendance logs and overall hours
- Faculty Dashboard for managing student check-in and check-out times
- Graceful error handling and intuitive UI design

## 🧠 Real-World Problem Solved

| Traditional Method        | This System                    |
|---------------------------|--------------------------------|
| Manual registers          | Digital tracking               |
| Human calculation errors  | Automatic hour calculation     |
| Easy data manipulation    | Secure authentication          |
| Hard to track history     | Instant dashboard view         |

---

## 🏁 Final System Outcome

- Secure login system  
- Accurate attendance tracking  
- Role-based dashboards  
- Automated time calculation  
- Error handling and validation  
- Scalable architecture  

