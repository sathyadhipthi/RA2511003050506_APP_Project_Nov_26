SkillMatch - Student Skill and Internship Matching System
Java 17, Swing, JDBC, MySQL 8.x (MySQL Connector/J on the classpath)

1. Run sql/schema.sql in MySQL.
2. Edit config.properties with your MySQL user/password.
3. Compile:  javac -cp mysql-connector-j.jar -d out $(find src -name '*.java')
4. Run:      java -cp out:mysql-connector-j.jar skillmatch.ui.LoginFrame
   (Windows: use ; instead of : in the classpath)
