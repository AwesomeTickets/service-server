#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import os

try:
    os.remove("nohup.out")
except Exception as e:
    pass

os.system("nohup mvn clean tomcat7:run-war &")
