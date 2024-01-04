# Sample how to use DSS Facade from Java KSeF Client

## Generating test electronic stamp

````shell
openssl req -newkey rsa:2048 -nodes -keyout key.pem -out certificate.csr -subj "/C=PL/ST=Mazowieckie/L=Warszawa/O=test-org/OU=IT/CN=test klient/emailAddress=kowalski@example.com/2.5.4.97=VATPL-2932110194"
openssl x509 -signkey key.pem -in certificate.csr -req -days 3650 -out certificate.pem
openssl pkcs12 -export -out stamp.p12 -inkey key.pem -in certificate.pem
````

# Signer implementation

For test, when do you have stamp in p12 file

````java
P12Signer signer = new P12Signer(new KeyStore.PasswordProtection("123ewqasd".toCharArray()), new File(tokenFile));
````

For Demo and Prod, when you are using real electronic stamp or digital signature

````java
PasswordInputCallback callback = new PrefilledPasswordCallback(new KeyStore.PasswordProtection("......".toCharArray()));
CardSigner signer = new CardSigner("/opt/proCertumSmartSign", "cryptoCertum3PKCS", 1, callback);
````

You need "driver" for your crypto device. The first param points to a path where a library resides, and the second one, point file name without an extension.

Library name for various suppliers:

- Certum: `cryptoCertum3PKCS`
- KIR: `ccgraphitep11` (linux: https://www.elektronicznypodpis.pl/gfx/elektronicznypodpis/userfiles/szafirsdk/szafir/sdk/lib/libccgraphitep11.so), Windows: `CCGraphiteP11p.x64`
- Eurocert: `cmP11` (they do not have linux driver)
