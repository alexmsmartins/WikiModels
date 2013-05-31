#!/bin/sh
set -u

# initialization
GLASSFISH_HOME=$HOME/glassfish
PATH=$GLASSFISH_HOME/bin:$PATH
SCRIPT_DIR=${PWD}
ASADMIN_AUTH="--user=admin --passwordfile=$SCRIPT_DIR/passwordfile.txt"

cd wm_server
mvn package
cd -
$HOME/glassfish/bin/asadmin deploy ${ASADMIN_AUTH} -s --contextroot wm_server ${SCRIPT_DIR}/wm_server/target/wm_server-0.1-SNAPSHOT.war

echo "Due to a asadmin handshake error when deploying a war with asadmin, the deployment should be done manually"
echo "To do that, please enter localhost:4848 in the browser > Enter user \"admin\" and password \"adminadmin\" and login."
echo "Then click Web Applications > Deploy > Location > Choose file and select wm_server/target/wm_server-0.1-SNAPSHOT.war manually. Then change Application name and Context Root to \"wm_server\" and press 'OK'"
