#!/bin/sh
sudo -v
echo "========================= STOPPING GLASSFISH DOMAIN ============================"
~/glassfish/bin/asadmin stop-domain 
echo "========================= STOPPING DERBY DATABASE ============================="
~/glassfish/bin/asadmin stop-database 
echo "======================== STOPPING POSTGRESQL DATABASE =========================="
sudo /etc/init.d/postgresql stop 9.1
