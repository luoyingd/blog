# How to run

### `Init mysql database`
run the initdb.sql\
set your own mysql address, username and password here\
<img src=/img/mysql.jpeg height=60% width=60%/>

### `Init redis server`
start your redis server\
set your own redis host and port here\
<img src=/img/redis.jpeg height=60% width=60%/>

### `Init your own mail service`
set your own email info here\
<img src=/img/mail.jpeg height=60% width=60%/>

### `Run`
#### `run locally`
1. cd to backend directory, run the following command\
mvn install -Dmaven.test.skip=true
2. cd to api/target directory, run the following command, then your service will be accessible via localhost:8090/api\
java -jar api-1.0-SNAPSHOT.jar
#### `run via docker`
1. cd to backend directory, run the following command\
mvn install -Dmaven.test.skip=true
2. cd to backend directory, run the following command, then your service will be accessible via localhost:9090/api\
docker build -t dlyblog:1.0 . \
docker run -d -p 9090:8090 -t dlyblog:1.0


