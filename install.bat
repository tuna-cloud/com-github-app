pushd %~dp0\application-home
set BASE_DIR=%CD%
popd
set APPLICATION_HOME=%BASE_DIR%

rem Remove all old jar
del %APPLICATION_HOME%\libs\*.jar
del %APPLICATION_HOME%\plugins\*.jar

rem copy new jar to destanation folder
copy %APPLICATION_HOME%\..\com-github-app\distribution\target\distribution-1.0.0-bin\libs\*.jar %APPLICATION_HOME%\libs
copy %APPLICATION_HOME%\..\com-github-app\com-github-app-runner\target\com-github-app-runner*.jar %APPLICATION_HOME%\libs
copy %APPLICATION_HOME%\..\com-github-app\com-github-app-agent\target\com-github-app-agent*.jar %APPLICATION_HOME%\plugins\java-agent.jar