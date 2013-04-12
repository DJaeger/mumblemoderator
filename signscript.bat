#"C:\Program Files\Java\jdk1.7.0_05\bin\keytool" -genkey -keystore keystore -alias myself

"C:\Program Files\Java\jdk1.7.0_05\bin\jarsigner" -keystore keystore ../jnlp_export/protobuf-java-2.3.0-lite.jar myself

"C:\Program Files\Java\jdk1.7.0_05\bin\jarsigner" -keystore keystore ../jnlp_export/jnlp_export.jar myself



