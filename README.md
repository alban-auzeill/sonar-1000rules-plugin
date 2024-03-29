# Test Sonar Plugin with 1000 rules

The plugin target rust files (*.rs)
The 1000 rules are generated by `src/main/java/one/thousand/rules/plugin/GenerateRules.java`

Check one analysis
```shell
mvn clean package
cp target/sonar-1000rules-plugin-1.0-SNAPSHOT.jar ../sonarqube-10.4.1.88267/extensions/plugins
cd project-to-analyse
export SONAR_TOKEN="<replace with sonar token>"
sonar-scanner
```

Expected output when there is no error
```txt
INFO: Rust Sensor executed with 1000 checks 0 missing: 
```
Expected output when there is an error
```txt
INFO: Rust Sensor executed with 1000 checks 2 missing: S0001, S0002 
```

Run the scanner in a loop to see if some checks are missing
```shell
while true; do sonar-scanner | grep 'Rust Sensor executed'; done
```
