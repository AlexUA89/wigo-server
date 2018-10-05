create tables for mac:
psql postgres -f create_tables.sql

create tables for ubuntu:
sudo -i -u postgres psql -f create_tables.sql

run tomcat on port 80:
1)
Edit TOMCAT/conf/server.xml and change port="8080" to "80"
2)
sudo apt-get install authbind
sudo touch /etc/authbind/byport/80
sudo chmod 500 /etc/authbind/byport/80
sudo chown ubuntu /etc/authbind/byport/80
3) create the file TOMCAT/bin/setenv.sh with the following content:
AUTHBIND=yes
CATALINA_OPTS="-Djava.net.preferIPv4Stack=true"
4) Change TOMCAT/bin/startup.sh:
exec authbind --deep "$PRGDIR"/"$EXECUTABLE" start "$@"
# OLD: exec "$PRGDIR"/"$EXECUTABLE" start "$@"

5) start postgres
/etc/init.d/postgresql start
