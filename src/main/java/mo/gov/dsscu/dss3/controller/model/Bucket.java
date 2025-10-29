package mo.gov.dsscu.dss3.controller.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "Bucket")
public class Bucket {
  @JacksonXmlProperty(localName = "BucketArn")
  private String bucketArn;

  @JacksonXmlProperty(localName = "BucketRegion")
  private String bucketRegion;

  @JacksonXmlProperty(localName = "CreationDate")
  private LocalDateTime creationDate;

  @JacksonXmlProperty(localName = "Name")
  private String name;

  public static Bucket of(mo.gov.dsscu.dss3.model.Bucket bucket) {
    Bucket b = new Bucket();
    b.setBucketArn("dss3");
    b.setBucketRegion("dsscu");
    b.setCreationDate(bucket.getCreatedAt());
    b.setName(bucket.getName());
    return b;
  }
}
