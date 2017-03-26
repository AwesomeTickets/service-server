#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import platform

if platform.system() == "Windows":
    os.system("mvn clean compile tomcat7:run")
elif platform.system() == "Linux":
    os.system("sudo mvn clean compile tomcat7:run")
