#!/bin/sh
########################
# install_config_kb.sh #
########################
#The  shell  shall write a message to standard error when it tries to expand a variable that is not set and immediately
# exit.
set -u

DOWNLOAD_DIR=$PWD/reproducible_env_downloads
SDB_ZIP="sdb-1.3.1-distribution.zip"
SCRIPT_DIR=${PWD}

# install SDB in $HOME
if [ -f $DOWNLOAD_DIR/$SDB_TAR ]; then
    echo "SDB 1.3.1 was downloaded before"
else
    wget -P $DOWNLOAD_DIR http://www.apache.org/dist/jena/binaries/$SDB_ZIP
fi

cd $DOWNLOAD_DIR
unzip ./$SDB_ZIP
if [ -d $HOME/sdb-1.3.1 ]; then
        echo "~/sdb-1.3.1 already exists."
        rm -r $HOME/sdb-1.3.1
fi
mv ./sdb-1.3.1 $HOME

