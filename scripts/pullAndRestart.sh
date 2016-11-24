TOMCAT_DIR="/home/ubuntu/apache-tomcat-9.0.0.M13"
WIGO_DIR="/home/ubuntu/wigo-server/"

sh "$TOMCAT_DIR/bin/shutdown.sh"
cd "$WIGO_DIR"
git pull https://2b6697f81aa09ea18fc28e8d5666fb8e7650e220@github.com/AlexUA89/wigo-server.git
mvn clean package
rm -rf "$TOMCAT_DIR/temp"
rm -rf "$TOMCAT_DIR/work"
rm -rf "$TOMCAT_DIR/webapps/wigo-server"
rm -rf "$TOMCAT_DIR/webapps/wigo-server.war"
cp -a "$WIGO_DIR/target/wigo-server.war $TOMCAT_DIR/webapps"
sh "$TOMCAT_DIR/bin/startup.sh"
