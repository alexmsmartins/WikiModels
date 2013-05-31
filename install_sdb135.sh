#!/bin/sh
########################
# install_config_kb.sh #
########################
#The  shell  shall write a message to standard error when it tries to expand a variable that is not set and immediately
# exit.
set -u

DOWNLOAD_DIR=$PWD/reproducible_env_downloads
SDB_TAR="jena-sdb-1.3.5-distribution.tar.gz"
export PATH=$SDBROOT/bin:$PATH
SCRIPT_DIR=${PWD}

# install SDB in $HOME
if [ -f $DOWNLOAD_DIR/$SDB_TAR ]; then
    echo "SDB 1.3.5 was downloaded before"
else
    wget -P $DOWNLOAD_DIR http://www.apache.org/dist/jena/binaries/$SDB_TAR
fi

if [ -f $DOWNLOAD_DIR/$SDB_TAR.md5 ]; then
    rm $DOWNLOAD_DIR/$SDB_TAR.md5
fi
# always pull md5 file again to check if there were changes to it. 
wget -P $DOWNLOAD_DIR http://www.apache.org/dist/jena/binaries/$SDB_TAR.md5
# TODO if the md5 key file changes, maybe the hashed file should be pulled again

sh $SCRIPT_DIR/check_md5.sh $DOWNLOAD_DIR/$SDB_TAR

RETVAL=$?

if [ "$RETVAL" -eq 0 ]; then
        echo "Jena SDB 1.3.5 integrity checked!"
else
        echo >&2 "Jena SDB 1.3.5 has an incorrect md5 hash. Aborting."
        exit $RETVAL
fi

cd $DOWNLOAD_DIR
tar zxvf ./$SDB_TAR
if [ -d $HOME/jena-sdb-1.3.5 ]; then
        echo "~/jena-sdb-1.3.5 already exists."
        rm -r $HOME/jena-sdb-1.3.5
fi
mv ./jena-sdb-1.3.5 $HOME

