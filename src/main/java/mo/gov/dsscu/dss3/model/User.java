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
import mo.gov.dsscu.dss3.utils.PasswordUtils;

@Entity
@Table(name = "users", indexes = {
    @jakarta.persistence.Index(name = "idx_username", columnList = "username"),
})
@Data
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = true, unique = true)
  private String email;

  @JsonIgnore
  @Column(nullable = true, length = 128)
  private String password;

  @JsonIgnore
  @Column(nullable = true, length = 16)
  private String salt;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @JsonIgnore
  @Column(name = "deleted_at", nullable = true)
  private LocalDateTime deletedAt;

  @Column(name = "role", nullable = true, length = 20)
  private String role;

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
  }

  public User changePassword(String newPassword) {
    String salt = PasswordUtils.generateSalt();
    this.salt = salt;
    try {
      this.password = PasswordUtils.hashPassword(newPassword, this.salt);
    } catch (Exception e) {
      throw new RuntimeException("Error hashing password", e);
    }
    return this;
  }
}
