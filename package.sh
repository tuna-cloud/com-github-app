#!/bin/bash
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific

cd $APPLICATION_HOME/../com-github-app
mvn clean package -Dmaven.test.skip=true

# Remove all old jar
rm -f $APPLICATION_HOME/libs/*.jar
rm -f $APPLICATION_HOME/plugins/*.jar

# copy new jar to destanation folder
cp -f $APPLICATION_HOME/../com-github-app/distribution/target/distribution*bin/libs/*.jar $APPLICATION_HOME/libs/
cp -f $APPLICATION_HOME/../com-github-app/com-github-app-runner/target/com-github-app-runner*.jar $APPLICATION_HOME/libs/
cp -f $APPLICATION_HOME/../com-github-app/com-github-app-agent/target/com-github-app-agent*.jar $APPLICATION_HOME/plugins/java-agent.jar
