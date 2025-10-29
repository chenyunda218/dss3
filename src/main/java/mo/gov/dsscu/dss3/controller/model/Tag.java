package mo.gov.dsscu.dss3.controller.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Tag")
public class Tag {
  @JacksonXmlProperty(localName = "Key")
  private String key;
  @JacksonXmlProperty(localName = "Value")
  private String value;
}
