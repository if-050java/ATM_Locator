mysql -u root -proot < dump.sql
start startup
timeout 10
start mvn -Ptomcat tomcat7:deploy
pause

