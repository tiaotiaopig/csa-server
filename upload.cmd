mvnw clean package -f pom.xml -Dmaven.test.skip=true && scp .\target\csa-server-0.0.1-SNAPSHOT.jar lifeng@192.168.50.17:/home/lifeng/Develop/backend/csa