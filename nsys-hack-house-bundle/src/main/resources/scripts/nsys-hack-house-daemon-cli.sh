#!/bin/bash

# NSYS_BASEDIR: The path to the Nsys installation. Its recommended to create a symbolic link to the latest version so
# the process will still work after upgrades.
#NSYS_BASEDIR="/opt/${nsys.dist.name}"

if [ "x$NSYS_BASEDIR" = "x" ]; then
	NSYS_BASEDIR="$( cd "$(dirname "$0")" ; pwd -P )/.."
    echo "Variable NSYS_BASEDIR is missing! Using default..." >&2
fi

$NSYS_BASEDIR/bin/nsys-launcher.sh nsys-daemon-cli run "$@"