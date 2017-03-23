#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os

fileName = 'server.tar.gz'
ip = '120.25.76.106'

os.system("rm -f %s" % fileName)
os.system("tar zcvf %s ./" % fileName)
os.system("scp ./%s sl@%s:~/server/" % (fileName, ip))
os.system("ssh sl@%s" % ip)
