package mo.gov.dsscu.dss3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mo.gov.dsscu.dss3.model.ObjectId;
import mo.gov.dsscu.dss3.model.S3Object;

public interface S3ObjectRepository extends JpaRepository<S3Object, ObjectId> {
  Optional<S3Object> findByBucketNameAndObjectKey(String bucketName, String objectKey);
}
