#!/bin/bash

### BEGIN INIT INFO
# Provides:          ${nsys.dist.name}-portal
# Required-Start:    $network $named $time
# Required-Stop:     $network $named $time
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: Nsys Portal
# Description:       Nsys Portal starts web portal for the Nsys Platform.
### END INIT INFO
 
APPLICATION_DESC="${nsys.dist.portal.name}"

# RUN_AS: The user to run Nsys Portal as. Its recommended that you create a separate user account for security reasons
RUN_AS=${nsys.dist.name}

# NSYS_BASEDIR: The path to the Nsys installation. Its recommended to create a symbolic link to the latest version so
# the process will still work after upgrades.
NSYS_BASEDIR="/opt/${nsys.dist.name}"

nsysctl() {
	if [[ "$2" != "run" && "x$USER" != "x$RUN_AS" ]]; then
		su - "$RUN_AS" -c "export NSYS_BASEDIR=$NSYS_BASEDIR;export APPLICATION_DESC=\"$APPLICATION_DESC\";$NSYS_BASEDIR/bin/$1 $2"
	else
		export NSYS_BASEDIR=$NSYS_BASEDIR
		export APPLICATION_DESC="$APPLICATION_DESC"
		$NSYS_BASEDIR/bin/$1 $2
	fi
}

case "$1" in
	start)
			nsysctl nsys-container.sh start
			;;
	stop)
			nsysctl nsys-container.sh stop
			;;
	restart)
			nsysctl nsys-container.sh stop
			sleep 10
			nsysctl nsys-container.sh start
			;;
	run)
			nsysctl nsys-container.sh run
			;;
	*)
			echo "Usage: $0 {start|stop|restart|run}"
esac
 
exit 0