System Requirement: 
Java 1.7 or above (apt-get install default-jdk)
jUnit4 (apt-get install junit4)
tomcat7(apt-get install tomcat7)

=================================================================

Instruction to setup environment:
1. Open Terminal
2. Head to project base directory (using ‘cd {folder location}’)
3. Run command ‘sudo sh setup_environment.sh’

=================================================================

Instruction to compile and deploy war file:
1. Open Terminal
2. Head to project base directory (using ‘cd {folder location}’)
3. Run command ‘sudo sh build_and_run.sh’
4. You can now access web service by url
‘localhost:8080/delectable/{your option}’
5. The war file will be in WebContent directory

=================================================================

Instruction to compile and run unit test:
1. Open Terminal
2. Head to project base directory (using ‘cd {folder location}’)
3. Run command ‘sudo sh unittest_build_and_run.sh’

=================================================================

License
Delectable is open-sourced software licensed under CC By 4.0

=================================================================

Known bugs
TestSuite is sensitive. Changing order might cause failure. (Individual test is not affected)

=================================================================

Credits and acknowledgements
1. Jersey - https://jersey.java.net/
2. Gson - https://github.com/google/gson

