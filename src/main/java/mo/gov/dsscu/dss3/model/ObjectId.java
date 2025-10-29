package mo.gov.dsscu.dss3.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class ObjectId implements Serializable {
  private String bucketName;
  private String objectKey;

  public ObjectId(String bucketName, String objectKey) {
    this.bucketName = bucketName;
    this.objectKey = objectKey;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ObjectId that = (ObjectId) o;
    return bucketName.equals(that.bucketName) && objectKey.equals(that.objectKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bucketName, objectKey);
  }
}
