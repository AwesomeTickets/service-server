#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os

cmd = 'sudo mvn tomcat7:run-war'

pid = os.popen("sudo ps -ef | grep '%s' | grep -v grep "
               "| awk '{ print $2 }'" % cmd).read()

if len(pid) > 0:
    os.system("sudo kill %s" % pid)

os.system("nohup %s &" % cmd)
