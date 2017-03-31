#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os

cmd = 'mvn clean tomcat7:run-war'

pid = os.popen("ps -ef | grep 'tomcat7:run-war' | grep -v grep "
               "| awk '{ print $2 }'").read()

if len(pid) > 0:
    os.system("sudo kill %s" % pid)

os.system("nohup %s &" % cmd)
