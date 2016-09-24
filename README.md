# Welcome to the #HackTheHouse project!

## Quick links

* [Nsys #HackTheHouse Project][1]
* [Nsys Platform Documentation][2]
* [How to install Nsys Plugin SDK][3]
* [How to install and configure Nsys Platform][4]
* [Issues][5]

## Description

The IoT dashboard showing data from sensors. The goal is to create an application showing metrics 
from various sensors (humidity, temperature etc) located in a smart home and/or building. 
Based on collected data the dashboard can provide analytic and predictions data such as energy saving, 
house happiness etc. Access to external data for dashboard is based on adapters where an individual 
adapter is providing access to data from particular vendor and/or IoT platform.

The Nsys #HackTheHouse implementation is based on the [Nsys Platform](http://www.nsys.org).

### Quick start

 * cd "project root"
 * ./build.sh
 * ./run-portal.sh
 * vi nsys-hack-house-bundle/target/nsys-hack-house/logs/nsys-hack-house-portal.log
 * Browse to [http://localhost:9260](http://localhost:9260). Check up live demo at [http://hack-house.nsys.org](http://hack-house.nsys.org)!
 * Use default user "admin" and password "admin" to login


[1]: http://doc.nsys.org/display/NSYS/Nsys+Hack+House
[2]: http://doc.nsys.org/display/NSYS/
[3]: http://doc.nsys.org/display/NSYS/Nsys+Plugin+SDK
[4]: http://doc.nsys.org/display/NSYS/Nsys+Installation+and+Configuration
[5]: http://jira.nsys.org/browse/NSYS