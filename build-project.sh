#!/bin/sh
#The  shell  shall write a message to standard error when it tries to expand a variable that is not set and immediately
# exit.
set -u
####################
# build-project.sh #
####################

git submodule update --init

if [ -d ../UsefullScalaStuff ]; then
        echo "UsefullScalaStuff is already here."
else
        cd ..
        git clone git@github.com:alexmsmartins/UsefullScalaStuff.git
        cd -
fi
cd ../UsefullScalaStuff
mvn install
cd -
cd wm_math_parser
mvn install
cd -
cd wm_library
mvn install
cd -
cd wm_libjsbml
mvn install
cd -
mvn install

