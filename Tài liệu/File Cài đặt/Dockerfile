# Start from the official Flink image
FROM flink:1.17.1-scala_2.12

###############################################
## Download Neccessary Jars to Flink Class Path
###############################################

## Iceberg Flink Library
RUN curl -L https://repo1.maven.org/maven2/org/apache/iceberg/iceberg-flink-runtime-1.17/1.4.2/iceberg-flink-runtime-1.17-1.4.2.jar -o /opt/flink/lib/iceberg-flink-runtime-1.17-1.4.2.jar

## Hive Flink Library
RUN curl -L https://repo1.maven.org/maven2/org/apache/flink/flink-sql-connector-hive-2.3.9_2.12/1.17.1/flink-sql-connector-hive-2.3.9_2.12-1.17.1.jar -o /opt/flink/lib/flink-sql-connector-hive-2.3.9_2.12-1.17.1.jar

## Hadoop AWS Classes
RUN curl -L https://repo.maven.apache.org/maven2/org/apache/flink/flink-shaded-hadoop-2-uber/2.8.3-10.0/flink-shaded-hadoop-2-uber-2.8.3-10.0.jar -o /opt/flink/lib/flink-shaded-hadoop-2-uber-2.8.3-10.0.jar

## AWS Bundled Classes
RUN curl -L https://repo1.maven.org/maven2/software/amazon/awssdk/bundle/2.20.18/bundle-2.20.18.jar -o /opt/flink/lib/bundle-2.20.18.jar

RUN curl -L https://repo1.maven.org/maven2/com/amazonaws/aws-java-sdk-bundle/1.11.1026/aws-java-sdk-bundle-1.11.1026.jar -o /opt/flink/lib/aws-java-sdk-bundle-1.11.1026.jar

RUN curl -L https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-aws/3.3.4/hadoop-aws-3.3.4.jar -o /opt/flink/lib/hadoop-aws-3.3.4.jar

RUN curl -L https://repo1.maven.org/maven2/org/apache/flink/flink-s3-fs-hadoop/1.17.1/flink-s3-fs-hadoop-1.17.1.jar -o /opt/flink/lib/flink-s3-fs-hadoop-1.17.1.jar

RUN curl -L https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-common/3.3.4/hadoop-common-3.3.4.jar -o /opt/flink/lib/hadoop-common-3.3.4.jar

RUN curl -L https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-hdfs/3.3.4/hadoop-hdfs-3.3.4.jar -o /opt/flink/lib/hadoop-hdfs-3.3.4.jar

RUN curl -L https://repo.maven.apache.org/maven2/org/apache/flink/flink-connector-kafka/3.0.1-1.17/flink-connector-kafka-3.0.1-1.17.jar  -o /opt/flink/lib/flink-connector-kafka-3.0.1-1.17.jar

RUN curl -L https://repo.maven.apache.org/maven2/org/apache/kafka/kafka-clients/3.5.1/kafka-clients-3.5.1.jar   -o /opt/flink/lib/kafka-clients-3.5.1.jar

RUN curl -L https://repo.maven.apache.org/maven2/org/slf4j/slf4j-api/1.7.36/slf4j-api-1.7.36.jar   -o /opt/flink/lib/slf4j-api-1.7.36.jar

RUN curl -L https://repo.maven.apache.org/maven2/org/slf4j/slf4j-log4j12/1.7.36/slf4j-log4j12-1.7.36.jar   -o /opt/flink/lib/slf4j-log4j12-1.7.36.jar

RUN curl -L https://repo1.maven.org/maven2/org/apache/flink/flink-connector-files/1.17.1/flink-connector-files-1.17.1.jar  -o /opt/flink/lib/flink-connector-files-1.17.1.jar

RUN curl -L https://repo.maven.apache.org/maven2/org/apache/flink/flink-java/1.17.1/flink-java-1.17.1.jar   -o /opt/flink/lib/flink-java-1.17.1.jar

RUN curl -L https://repo.maven.apache.org/maven2/org/apache/flink/flink-streaming-java/1.17.1/flink-streaming-java-1.17.1.jar   -o /opt/flink/lib/flink-streaming-java-1.17.1.jar

RUN curl -L https://repo.maven.apache.org/maven2/org/apache/flink/flink-clients/1.17.1/flink-clients-1.17.1.jar  -o /opt/flink/lib/flink-clients-1.17.1.jar

RUN curl -L https://repo1.maven.org/maven2/org/apache/iceberg/iceberg-flink/1.4.2/iceberg-flink-1.4.2.jar -o /opt/flink/lib/iceberg-flink-1.4.2.jar

RUN curl -L https://repo1.maven.org/maven2/org/apache/flink/flink-table-api-java-bridge/1.17.1/flink-table-api-java-bridge-1.17.1.jar   -o /opt/flink/lib/flink-table-api-java-bridge-1.17.1.jar

RUN curl -L https://repo1.maven.org/maven2/org/apache/flink/flink-table-api-java/1.17.1/flink-table-api-java-1.17.1.jar -o /opt/flink/lib/flink-table-api-java-1.17.1.jar

RUN curl -L https://repo1.maven.org/maven2/org/apache/flink/flink-table-common/1.17.1/flink-table-common-1.17.1.jar -o /opt/flink/lib/flink-table-common-1.17.1.jar
RUN curl -L https://repo1.maven.org/maven2/software/amazon/awssdk/url-connection-client/2.20.18/url-connection-client-2.20.18.jar -o /opt/flink/lib/url-connection-client-2.20.18.jar
RUN curl -L https://repo1.maven.org/maven2/org/apache/iceberg/iceberg-core/1.4.2/iceberg-core-1.4.2.jar -o /opt/flink/lib/iceberg-core-1.4.2.jar
RUN curl -L  https://repo1.maven.org/maven2/junit/junit/4.11/junit-4.11.jar -o /opt/flink/lib/junit-4.11.jar


#--- Minio -----
RUN curl -L https://repo1.maven.org/maven2/io/minio/minio/8.5.5/minio-8.5.5.jar -o /opt/flink/lib/minio-8.5.5.jar
RUN curl -L https://repo.maven.apache.org/maven2/org/json/json/20250517/json-20250517.jar  -o /opt/flink/lib/json-20250517.jar
RUN curl -L  https://repo1.maven.org/maven2/com/google/guava/guava/31.1-jre/guava-31.1-jre.jar  -o /opt/flink/lib/guava-31.1-jre.jar
RUN curl -L https://repo1.maven.org/maven2/com/squareup/okhttp3/okhttp/4.10.0/okhttp-4.10.0.jar  -o /opt/flink/lib/okhttp-4.10.0.jar
RUN curl -L https://repo1.maven.org/maven2/com/squareup/okio/okio/2.10.0/okio-2.10.0.jar  -o /opt/flink/lib/okio-2.10.0.jar
RUN curl -L https://repo1.maven.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/1.7.10/kotlin-stdlib-1.7.10.jar  -o /opt/flink/lib/kotlin-stdlib-1.7.10.jar
RUN curl -L https://repo1.maven.org/maven2/org/simpleframework/simple-xml/2.7.1/simple-xml-2.7.1.jar   -o /opt/flink/lib/simple-xml-2.7.1.jar
RUN curl -L https://repo1.maven.org/maven2/com/fasterxml/jackson/dataformat/jackson-dataformat-xml/2.13.4/jackson-dataformat-xml-2.13.4.jar  -o /opt/flink/lib/jackson-dataformat-xml-2.13.4.jar
RUN curl -L https://repo1.maven.org/maven2/jakarta/xml/bind/jakarta.xml.bind-api/4.0.0/jakarta.xml.bind-api-4.0.0.jar  -o /opt/flink/lib/jakarta.xml.bind-api-4.0.0.jar
RUN curl -L https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.13.4/jackson-databind-2.13.4.jar  -o /opt/flink/lib/jackson-databind-2.13.4.jar
RUN curl -L https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.13.4/jackson-annotations-2.13.4.jar   -o /opt/flink/lib/jackson-annotations-2.13.4.jar
RUN curl -L https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.13.4/jackson-core-2.13.4.jar   -o /opt/flink/lib/jackson-core-2.13.4.jar 
RUN curl -L https://repo1.maven.org/maven2/org/projectlombok/lombok/1.18.38/lombok-1.18.38.jar  -o /opt/flink/lib/lombok-1.18.38.jar
RUN curl -L https://repo1.maven.org/maven2/org/codehaus/woodstox/stax2-api/4.2.2/stax2-api-4.2.2.jar  -o /opt/flink/lib/stax2-api-4.2.2.jar
RUN curl -L https://repo1.maven.org/maven2/com/fasterxml/woodstox/woodstox-core/6.5.1/woodstox-core-6.5.1.jar  -o /opt/flink/lib/woodstox-core-6.5.1.jar


RUN curl -L https://repo1.maven.org/maven2/org/json/json/20250517/json-20250517.jar -o /opt/flink/lib/json-20250517.jar
RUN curl -L https://repo1.maven.org/maven2/org/apache/parquet/parquet-avro/1.15.2/parquet-avro-1.15.2.jar -o /opt/flink/lib/parquet-avro-1.15.2.jar
RUN curl -L https://repo1.maven.org/maven2/org/apache/flink/flink-parquet/1.17.1/flink-parquet-1.17.1.jar   -o /opt/flink/lib/flink-parquet-1.17.1.jar

RUN curl -L https://repo1.maven.org/maven2/org/apache/flink/flink-runtime/1.17.1/flink-runtime-1.17.1.jar -o /opt/flink/lib/flink-runtime-1.17.1.jar

RUN curl -L https://repo.maven.apache.org/maven2/org/apache/flink/flink-connector-base/1.17.1/flink-connector-base-1.17.1.jar -o /opt/flink/lib/flink-connector-base-1.17.1.jar
RUN curl -L https://repo1.maven.org/maven2/com/github/ben-manes/caffeine/caffeine/3.1.8/caffeine-3.1.8.jar  -o /opt/flink/lib/caffeine-3.1.8.jar

RUN curl -L https://repo1.maven.org/maven2/org/apache/avro/avro/1.11.1/avro-1.11.1.jar  -o /opt/flink/lib/avro-1.11.1.jar

## Install Nano to edit files
RUN apt update && apt install -y nano

CMD ["./bin/start-cluster.sh"]

## docker build --tag alexmerced/flink-iceberg-1.3.1