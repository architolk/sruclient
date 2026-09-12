keytool -importcert -trustcacerts -alias overheidnl -file target-ca.pem -keystore "$JAVA_HOME/lib/security/cacerts" -storepass changeit -noprompt
