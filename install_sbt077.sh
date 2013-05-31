#!/bin/sh
# let's trigger 
set -u

SBT_JAR=sbt-launch-0.7.7.jar
SBT_SCRIPT=sbt-0.7.7

wget -P $HOME/bin http://simple-build-tool.googlecode.com/files/${SBT_JAR}

echo "java -Xmx512M -jar \${HOME}/bin/${SBT_JAR} \"\$@\"" > ${HOME}/bin/${SBT_SCRIPT}
chmod u+x $HOME/bin/${SBT_SCRIPT}
