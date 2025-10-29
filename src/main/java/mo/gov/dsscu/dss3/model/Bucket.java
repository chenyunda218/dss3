package mo.gov.dsscu.dss3.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "buckets")
@Data
@SQLDelete(sql = "UPDATE buckets SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Bucket {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, unique = true, length = 100)
  private String name;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @JsonIgnore
  @Column(name = "deleted_at", nullable = true)
  private LocalDateTime deletedAt;

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
  }
}
