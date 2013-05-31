#!/bin/sh
########################
# install_config_kb.sh #
########################
#The  shell  shall write a message to standard error when it tries to expand a variable that is not set and immediately
# exit.
set -u

DOWNLOAD_DIR=$PWD/reproducible_env_downloads
#exports to make these variables available to SDB scripts
export SDBROOT=$HOME/jena-sdb-1.3.5
export SDB_USER="root"
export SDB_PASSWORD="root"
POSTGRES_JDBC="postgresql-9.1-903.jdbc4.jar"
export SDB_JDBC="$DOWNLOAD_DIR/$POSTGRES_JDBC"
SDB_TAR="jena-sdb-1.3.5-distribution.tar.gz"
SDB_TAR="jena-sdb-1.3.5-distribution.tar.gz"
export PATH=$SDBROOT/bin:$PATH
SCRIPT_DIR=${PWD}

# same info as in wm_setup/sdb.ttl
PSQL_USER="root"
PSQL_PASSWORD="root"
PSQL_DB="wmstore"

# install sdb 1.3.5 in the user home directory
sh install_sdb135.sh

# download jdbc driver for postgres
if [-f $POSTGRES_JDBC ]; then
	echo "Postgresql 9.1 JDBC driver was downloaded before"
else
	wget -P $DOWNLOAD_DIR http://jdbc.postgresql.org/download/$POSTGRES_JDBC
fi

# configure postgresql wmstore database
# TODO delete this line later
sudo apt-get install postgresql-9.1
#sudo adduser mykbuser
sudo -u postgres createuser --superuser $PSQL_USER &&  #from regular shell
echo "ALTER USER root with encrypted password '$PSQL_PASSWORD';" | sudo -u postgres psql
sudo -u postgres createdb -O $PSQL_USER $PSQL_DB && 
echo "Creating user root and database wmstore completed!"

echo "Change postgresql permissions to allow connections via TCP/IP."
echo "Use http://linuxpoison.blogspot.tw/2012/01/how-to-install-configure-postgresql.html"

read -p "Press a key after completing the configuration" -n 1 

sdbconfig -v --sdb=$SCRIPT_DIR/wm_setup/sdb.ttl --create
