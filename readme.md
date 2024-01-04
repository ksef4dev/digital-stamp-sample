# Sample how to use DSS Facade from Java KSeF Client

## Generating test electronic stamp

````shell
openssl req -newkey rsa:2048 -nodes -keyout key.pem -out certificate.csr -subj "/C=PL/ST=Mazowieckie/L=Warszawa/O=test-org/OU=IT/CN=test klient/emailAddress=kowalski@example.com/2.5.4.97=VATPL-2932110194"
openssl x509 -signkey key.pem -in certificate.csr -req -days 3650 -out certificate.pem
openssl pkcs12 -export -out stamp.p12 -inkey key.pem -in certificate.pem
````
