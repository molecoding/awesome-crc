# App


Package and run jar,

```shell
# Linux/macOS
./mvn package
# Windows
mvnw.cmd package

java -jar target/awesome-crc-1.0-SNAPSHOT.jar
```

Or run a class directly,

```shell
# Linux/macOS
./mvnw exec:java -Dexec.mainClass=com.molecoding.nobs.App

# Windows
mvnw.cmd exec:java -Dexec.mainClass=com.molecoding.nobs.App
```
