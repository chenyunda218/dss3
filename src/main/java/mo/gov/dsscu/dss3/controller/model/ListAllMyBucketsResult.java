package mo.gov.dsscu.dss3.controller.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@JacksonXmlRootElement(localName = "ListAllMyBucketsResult")
@Data
public class ListAllMyBucketsResult {

  @JacksonXmlProperty(localName = "Owner")
  private Owner owner;

  @JacksonXmlProperty(localName = "Buckets")
  private List<Bucket> buckets;

  @JacksonXmlProperty(localName = "Prefix")
  private String prefix;

  @JacksonXmlProperty(localName = "ContinuationToken")
  private String continuationToken;

  public ListAllMyBucketsResult() {
    this.buckets = new java.util.ArrayList<>();
  }
}
