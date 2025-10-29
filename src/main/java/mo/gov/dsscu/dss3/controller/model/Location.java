package mo.gov.dsscu.dss3.controller.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Location")
public class Location {
  @JacksonXmlProperty(localName = "Name")
  private String name;

  @JacksonXmlProperty(localName = "Type")
  private String type;
}
