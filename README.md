# SkillMatch

## Course Details
Course ID: 21CSC203P
Course Name: Advanced Programming Practice (APP)

## Team Members
1. S. Sathya Dhipthi (Individual Project)
   Register Number: RA2511003050506
   Department: CSE
   Section: <add your section>
   Academic Year: <add your academic year>

## Mentor Details
Mentor Name: Dr. D. Deena Rose
Designation: Assistant Professor, Department of Computer Science and Engineering

## Project Title
SkillMatch – Student Skill and Internship Matching System

## Description
SkillMatch is a Java desktop application that connects students with internships that fit their skills. Students record their skills, and companies post internships with required skills. A transparent rule-based algorithm calculates a match percentage, lists the missing skills, and ranks internships from best to worst fit.

Students can apply for internships and track their application status. Companies can post internships and update applicant status. Administrators verify postings and manage users.

Match Score (%) = (matched required skills ÷ total required skills) × 100

## Key Features
- Role-based login for students, companies and administrators
- Student profiles with skills
- Internship postings with required skills and eligibility criteria
- Skill-match score, missing-skill list and ranked recommendations
- Application submission and status tracking
- Background matching with SwingWorker, so the UI stays responsive
- Salted PBKDF2 password hashing and parameterised SQL queries

## Technologies Used
- Java 17
- Java Swing (GUI)
- JDBC with MySQL Connector/J
- MySQL 8.x
- Git

## Project Structure
- `sql/schema.sql` – database tables
- `src/skillmatch/` – packages `model`, `dao`, `service`, `ui`, `util`, `exception`
- `config.properties` – database login settings

## How to Run
1. Install Java 17, MySQL 8.x and the MySQL Connector/J jar.
2. Run `sql/schema.sql` in MySQL to create the database and tables.
3. Edit `config.properties` with your MySQL user and password.
4. Compile:
   `javac -cp mysql-connector-j.jar -d out $(find src -name '*.java')`
5. Run:
   `java -cp out:mysql-connector-j.jar skillmatch.ui.LoginFrame`
   (On Windows, use `;` instead of `:` in the classpath.)

## Project Report
The full report is in this repository: `SkillMatch_Project_Report.pdf`
