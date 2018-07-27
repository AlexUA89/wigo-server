set -eo pipefail

WIGO_DIR="/root/wigo-server"

sudo pkill -f 'java -jar'
cd "$WIGO_DIR"
git pull https://9565d92cc7e245e3e476c481966694e87401035f@github.com/AlexUA89/wigo-server.git
rm -rf "$TOMCAT_DIR/target"
mvn clean package
java -jar ./target/server-1.0-SNAPSHOT.jar > "$TOMCAT_DIR/log.txt" 2>&1 &
