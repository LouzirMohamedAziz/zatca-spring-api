# zatca-spring-api

**This project is a Spring boot application that Interacts with the ZATCA E-INVOICING SDK to sign e-invoices.**

**1) to be able to run Zatca Spring API properly, we have to install the zatca-einvoicing-sdk-234-R3.2.0.jar ( located in /lib/zatca-einvoicing-sdk-234-R3.2.0/Apps/) locally in maven first by entering the following command: **
mvn install:install-file -Dfile=./lib/zatca-einvoicing-sdk-234-R3.2.0/Apps/zatca-einvoicing-sdk-234-R3.2.0.jar -DgroupId=com.example -DartifactId=zatca-einvoicing-sdk -Dversion=234-R3.2.0 -Dpackaging=jar


**2) the API schema is as follow: **
InvoiceRequest{
invoiceName	string
invoicePath	string
invoiceSignId	integer($int32)
signedInvoiceName	string
}

**3) to sign an Invoice, a post request should be performed within the following endpoint: (http://localhost:8082/invoice)
considering the following Body input as an example: **
{
  "invoicePath": "path_where_the_invoice_is_located",
  "invoiceName": "name_of_the_invoice_.xml",
  "signedInvoiceName": "name_of_the_invoice_signed.xml"
}
**NB:** By convention, the folder that is meant to play the role of Input/Output Device is : /lib/zatca-einvoicing-sdk-234-R3.2.0/