package mo.gov.dsscu.dss3.controller.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "ListBucketResult")
public class ListBucketResult {
  @JacksonXmlProperty(localName = "Name")
  private String name;

  @JacksonXmlProperty(localName = "Prefix")
  private String prefix;

  @JacksonXmlProperty(localName = "KeyCount")
  private Integer keyCount;

  @JacksonXmlProperty(localName = "MaxKeys")
  private Integer maxKeys;

  // IsTruncated
  @JacksonXmlProperty(localName = "IsTruncated")
  private Boolean isTruncated;
}
