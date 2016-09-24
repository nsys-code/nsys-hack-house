Nsys #HackTheHouse
==================

Version: ${build.number}


About
----------------------

The IoT dashboard showing data from sensors. The goal is to create an application 
showing metrics from various sensors (humidity, temperature etc) located in a smart 
home and/or building. Based on collected data the dashboard can provide analytic 
and predictions data such as energy saving, house happiness etc. Access to external 
data for dashboard is based on adapters where an individual adapter is providing 
access to data from particular vendor and/or IoT platform.

The Nsys #HackTheHouse implementation is based on the Nsys Platform.

Please see more details at http://doc.nsys.org/display/NSYS/Nsys+Hack+House


Quick Installation
----------------------

Requirements:
* Oracle JDK 1.7+ or Open JDK 1.7+ (Java)

If your system does not meet the above requirements, please read the
installation documentation: http://doc.nsys.org


### Linux and Mac

1. At first we need to set correct permissions for scripts
	$ chmod -R 755 <Nsys installation directory>/bin/*.sh

2. Edit `<Nsys installation directory>/bin/nsys-daemon.env.sh`

3. Set `NSYS_HOME` by uncommenting the `NSYS_HOME` line and adding the
   absolute path to the directory where you want Nsys to store your data.

4. In a terminal, run:
    `<Nsys installation directory>/bin/nsys-launcher.sh nsys-daemon run`

5. In your browser go to:
    `http://localhost:9280`

6. To run Nsys Daemon as system service you need to follow instructions below:

	NOTE: In example below the <Nsys installation directory> is /opt/${nsys.dist.name}
	
	$ adduser --system --shell /bin/bash --gecos 'Nsys Platform Control' --group --disabled-password --home /opt/${nsys.dist.name} ${nsys.dist.name}
	$ cp /opt/${nsys.dist.name}/bin/${nsys.dist.name}-daemon.sh /etc/init.d/${nsys.dist.name}-daemon
	$ chmod a+x /etc/init.d/${nsys.dist.name}-daemon
	$ update-rc.d ${nsys.dist.name}-daemon defaults
	$ /etc/init.d/${nsys.dist.name}-daemon start


More information about installation and configuration you can find at
http://doc.nsys.org/display/NSYS/Nsys+Installation+and+Configuration


Licensing
----------------------

See http://nsys.org/license
