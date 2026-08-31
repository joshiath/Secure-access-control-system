# Secure-access-control-system
building a console-based information system. The system has multiple users, and not every user should be allowed to see the same information. Your job is to create a Java application that loads account data from a file, authenticates a user's credentials, determines the user's role, and displays only the information authorized for that role.
Required System Behavior
Load user account records from an external file.
Prompt the user for a username and password.
Reject invalid credentials without revealing protected information.
Identify the authenticated user's authorization role.
Support at least three roles: STUDENT, TEACHER, and ADMIN.
Load information from an external protected-information file.
Display PUBLIC information to every authenticated user.
Display STUDENT information to STUDENT, TEACHER, and ADMIN users.
Display TEACHER information only to TEACHER and ADMIN users.
Display ADMIN information only to ADMIN users.
Handle missing files and malformed records without crashing.
Organize the solution into logical methods rather than placing the entire program in main().
Important Classroom Security Rule
Use fictional classroom credentials only. Do not use your real school, email, banking, social-media, or personal passwords. The starter version uses plain-text classroom passwords only so the authentication process can be observed. Plain-text password storage is not appropriate for a real production system.