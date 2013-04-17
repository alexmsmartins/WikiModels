#!/bin/sh
############################
# create-userauth-realm.sh #
############################

# initialization
GLASSFISH_HOME=$HOME/glassfish
PATH=$GLASSFISH_HOME/bin:$PATH
SCRIPT_DIR=${PWD}
ASADMIN_AUTH="--user=admin --passwordfile=$SCRIPT_DIR/passwordfile.txt"
DERBY_HOME=$GLASSFISH_HOME/javadb
DERBY_PORT=1527
DB_HOME=$HOME/tmp
DB_HOST=0.0.0.0


echo "asadmin start-domain"
asadmin start-domain
# create connection pool
echo "asadmin list-jdbc-connection-pools"
asadmin list-jdbc-connection-pools $ASADMIN_AUTH

echo "asadmin create-jdbc-connection-pool"
asadmin create-jdbc-connection-pool --datasourceclassname org.apache.derby.jdbc.ClientDataSource --restype javax.sql.DataSource $ASADMIN_AUTH \
--property \
ConnectionAttributes=\;create\\=true:\
DatabaseName=userauth:\
LoginTimeout=0:\
Password=APP:\
PortNumber=1527:\
RetrieveMessageText=true:\
SecurityMechanism=4:\
ServerName=localhost:\
TraceFileAppend=false:\
TraceLevel=-1:\
User=APP \
userauth

echo "asadmin start-database"
asadmin start-database --dbhost $DB_HOST --dbport $DERBY_PORT --dbhome $DB_HOME

echo "asadmin ping-connection-pool"
asadmin ping-connection-pool $ASADMIN_AUTH userauth

# create jdbc resource to userauth database
echo "asadmin create-jdbc-resource"
asadmin create-jdbc-resource --connectionpoolid userauth $ASADMIN_AUTH jdbc/userauth
