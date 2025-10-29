package mo.gov.dsscu.dss3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mo.gov.dsscu.dss3.model.Bucket;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
  public Optional<Bucket> findByName(String name);
}
