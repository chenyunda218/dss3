package mo.gov.dsscu.dss3.model;

import java.io.IOException;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@IdClass(ObjectId.class)
@Entity
@Table(name = "object_versions")
public class ObjectVersion {
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

  @Column(name = "path", nullable = false, unique = true, length = 500)
  private String path;

  @Column(nullable = false, length = 50)
  private String provider;

  @Column(nullable = false)
  private Integer version;

  public byte[] read() throws IOException {
    java.nio.file.Path filePath = java.nio.file.Paths.get(path);
    return java.nio.file.Files.readAllBytes(filePath);
  }
}
