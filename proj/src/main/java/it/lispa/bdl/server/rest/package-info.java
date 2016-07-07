@javax.xml.bind.annotation.XmlSchema(
	namespace = "http://www.loc.gov/METS/",
    xmlns = { 
		@javax.xml.bind.annotation.XmlNs( prefix = "mets",
          namespaceURI = "http://www.loc.gov/METS/"),
        
        @javax.xml.bind.annotation.XmlNs(prefix = "dc",
           namespaceURI = "http://purl.org/dc/elements/1.1/"),
           
      	@javax.xml.bind.annotation.XmlNs(prefix = "xlink",
           namespaceURI = "http://www.w3.org/1999/xlink"),
          
        @javax.xml.bind.annotation.XmlNs(prefix = "europeana",
           namespaceURI = "http://www.europeana.eu/schemas/ese/"),
    },
    elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED
)
package it.lispa.bdl.server.rest;
