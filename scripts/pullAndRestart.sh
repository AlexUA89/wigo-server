sh /root/apache-tomcat-9.0.0.M10/bin/shutdown.sh
cd /root/wigo-server
git pull https://2b6697f81aa09ea18fc28e8d5666fb8e7650e220@github.com/AlexUA89/wigo-server.git
mvn clean package
rm -rf /root/apache-tomcat-9.0.0.M10/temp
rm -rf /root/apache-tomcat-9.0.0.M10/work
rm -rf /root/apache-tomcat-9.0.0.M10/webapps/wigo-server
rm -rf /root/apache-tomcat-9.0.0.M10/webapps/wigo-server.war
cp -a /root/wigo-server/target/wigo-server.war /root/apache-tomcat-9.0.0.M10/webapps
sh /root/apache-tomcat-9.0.0.M10/bin/startup.sh
