rem !/bin/bash
rem Licensed to the Apache Software Foundation (ASF) under one or more
rem contributor license agreements.  See the NOTICE file distributed with
rem this work for additional information regarding copyright ownership.
rem The ASF licenses this file to You under the Apache License, Version 2.0
rem (the "License"); you may not use this file except in compliance with
rem the License.  You may obtain a copy of the License at
rem
rem    http://www.apache.org/licenses/LICENSE-2.0
rem
rem Unless required by applicable law or agreed to in writing, software
rem distributed under the License is distributed on an "AS IS" BASIS,
rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
rem See the License for the specific
pushd %~dp0\application-home
set BASE_DIR=%CD%
popd
set APPLICATION_HOME=%BASE_DIR%

pushd %APPLICATION_HOME%\..\com-github-app
mvn clean package -Dmaven.test.skip=true
popd

pause
rem Remove all old jar
rem del %APPLICATION_HOME%\libs\*.jar
rem del %APPLICATION_HOME%\plugins\*.jar

rem copy new jar to destanation folder
rem copy %APPLICATION_HOME%\..\com-github-app\distribution\target\distribution-1.0.0-bin\libs\*.jar %APPLICATION_HOME%\libs
rem copy %APPLICATION_HOME%\..\com-github-app\com-github-app-runner\target\com-github-app-runner*.jar %APPLICATION_HOME%\libs
