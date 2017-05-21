#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os
import sys

test = False
background = False
var_urand = ''
var_skip_test = '-Dmaven.test.skip=true '

for arg in sys.argv[1:]:
    if arg == '-t':
        test = True
    elif arg == '-b':
        background = True
    elif arg == '-u':
        # http://nobodyiam.com/2016/06/07/tomcat-startup-slow/
        var_urand = '-Djava.security.egd=file:/dev/./urandom '

cmd_test = 'mvn clean test'
cmd_background = 'nohup mvn clean tomcat7:run-war %s &' % \
    (var_urand + var_skip_test)
cmd_foreground = 'mvn clean tomcat7:run %s' % (var_urand)

if test:
    print("exec: " + cmd_test)
    sys.exit(os.system(cmd_test))
elif background:
    try:
        os.remove("nohup.out")
    except Exception as e:
        pass
    print("exec: " + cmd_background)
    os.system(cmd_background)
else:
    print("exec: " + cmd_foreground)
    os.system(cmd_foreground)
