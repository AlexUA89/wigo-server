set -eo pipefail

TOMCAT_DIR="/home/ubuntu/apache-tomcat-9.0.0.M13"
WIGO_DIR="/home/ubuntu/wigo-server"

sh "$TOMCAT_DIR/bin/shutdown.sh"
cd "$WIGO_DIR"
git pull https://2b6697f81aa09ea18fc28e8d5666fb8e7650e220@github.com/AlexUA89/wigo-server.git
mvn clean package
rm -rf "$TOMCAT_DIR/temp"
rm -rf "$TOMCAT_DIR/work"
rm -rf "$TOMCAT_DIR/webapps/ROOT"
mkdir "$TOMCAT_DIR/webapps/ROOT"
cp -a "$WIGO_DIR/target/wigo-server.war" "$TOMCAT_DIR/webapps/ROOT"
cd "$TOMCAT_DIR/webapps/ROOT"
unzip wigo-server.war
rm wigo-server.war
cp -r /home/ubuntu/wigo-static/* .
sed -i 's|http://52.90.115.129:8080/wigo-server||g' static/js/app.b2d4054c2e74e02cf8fc.js*
sh "$TOMCAT_DIR/bin/startup.sh"
