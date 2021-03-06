#!/bin/sh
sudo -v
echo "========================= STARTING GLASSFISH DOMAIN ============================"
~/glassfish/bin/asadmin start-domain 
echo "========================== STARTING DERBY DATABASE ============================="
~/glassfish/bin/asadmin start-database --dbhost 0.0.0.0 --dbport 1527 --dbhome ~/tmp
echo "======================== STARTING POSTGRESQL DATABASE =========================="
sudo /etc/init.d/postgresql start 9.1
echo "=============================== END OF SCRIPT ===================================="

