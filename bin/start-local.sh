#!/bin/bash

#set -x

for env in SPARK_HOME ; do
  if [[ -z "${!env}" ]]; then
    echo "$env must be set to run this script"
    exit 1
  else
    echo ${env}=${!env}
  fi
done

if [[ -z "${SQLPOWER_HOME}" ]]; then
  export SQLPOWER_HOME="$(cd "`dirname "$0"`"/../; pwd)"
fi

echo "SQLPOWER_HOME=$SQLPOWER_HOME"

JARS=$(find ${SQLPOWER_HOME}/jars -name "*.jar" | tr ' ' ',')

echo "JARS=$JARS"

MAIN_JAR=$(find ${SQLPOWER_HOME}/*/target -type f -name "*.jar" \
| grep 'sqlpower-powsql' |grep -v "sources" | grep -v "original")

echo "MAIN_JAR=$MAIN_JAR"

export DRIVER_MEMORY=${DRIVER_MEMORY:-2g}
${SPARK_HOME}/bin/spark-submit --class dt.powsql.core.server.ServerApp \
        --driver-memory ${DRIVER_MEMORY} \
        --jars ${JARS} \
        --master "local[*]" \
        --name SQLPower \
        --conf "spark.driver.extraJavaOptions"="-DREALTIME_LOG_HOME=$SQLPOWER_HOME/logs" \
        --conf "spark.sql.hive.thriftServer.singleSession=true" \
        --conf "spark.kryoserializer.buffer=256k" \
        --conf "spark.kryoserializer.buffer.max=1024m" \
        --conf "spark.serializer=org.apache.spark.serializer.KryoSerializer" \
        --conf "spark.scheduler.mode=FAIR" \
        ${MAIN_JAR}    \
        -sqlpower.name SQLPower    \
        -sqlpower.platform spark   \
        -sqlpower.rest true   \
        -sqlpower.rest.port 9003
