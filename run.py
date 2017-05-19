#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os
import sys

test = False
background = False

for arg in sys.argv[1:]:
    if arg == '-t':
        test = True
    elif arg == '-b':
        background = True

if test:
    os.system("mvn clean test")
elif background:
    try:
        os.remove("nohup.out")
    except Exception as e:
        pass
    os.system("nohup mvn -Dmaven.test.skip=true clean tomcat7:run-war &")
else:
    os.system("mvn clean tomcat7:run")
