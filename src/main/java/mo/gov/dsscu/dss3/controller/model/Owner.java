package mo.gov.dsscu.dss3.controller.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Owner")
public class Owner {

  @JacksonXmlProperty(localName = "DisplayName")
  private String displayName;

  @JacksonXmlProperty(localName = "ID")
  private String id;

  public Owner(String displayName, String id) {
    this.displayName = displayName;
    this.id = id;
  }

}
