package mo.gov.dsscu.dss3.model;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@IdClass(ObjectId.class)
@Entity
@Table(name = "s3_objects")
public class S3Object {
  @Id
  private String bucketName;

  @Id
  private String objectKey;

  @Column(name = "size", nullable = false)
  private Long size;

  @Column(name = "content_type", nullable = false, length = 100)
  private String contentType;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Column(name = "path", nullable = false, unique = true, length = 500)
  private String path;

  @Column(nullable = false, length = 50)
  private String provider;

  @Column(nullable = false)
  private Integer version;

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  public void write(byte[] data) {
    try (FileOutputStream fos = new FileOutputStream(path)) {
      fos.write(data);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public byte[] read() throws IOException {
    java.nio.file.Path filePath = java.nio.file.Paths.get(path);
    return java.nio.file.Files.readAllBytes(filePath);
  }

  public ObjectVersion toObjectVersion() {
    ObjectVersion version = new ObjectVersion();
    version.setBucketName(this.bucketName);
    version.setObjectKey(this.objectKey);
    version.setContentType(this.contentType);
    version.setSize(this.size);
    version.setPath(this.path);
    version.setProvider(this.provider);
    version.setCreatedAt(this.createdAt);
    version.setVersion(this.version);
    return version;
  }

}
