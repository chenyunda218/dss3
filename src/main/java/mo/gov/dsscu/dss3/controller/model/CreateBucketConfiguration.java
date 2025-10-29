package mo.gov.dsscu.dss3.controller.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "CreateBucketConfiguration")
public class CreateBucketConfiguration {
  @Nullable
  @JacksonXmlProperty(localName = "LocationConstraint")
  private String locationConstraint;

  // @Nullable
  // @JacksonXmlProperty(localName = "Location")
  // private Location location;
  // @Nullable
  // @JacksonXmlProperty(localName = "Tags")
  // private List<Tag> tags;
}
