package mo.gov.dsscu.dss3.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mo.gov.dsscu.dss3.model.Bucket;
import mo.gov.dsscu.dss3.model.ObjectId;
import mo.gov.dsscu.dss3.model.ObjectVersion;
import mo.gov.dsscu.dss3.model.S3Object;

@Service
public class BucketService {

  final static String FOLDER = "data";

  @Autowired
  private mo.gov.dsscu.dss3.repository.BucketRepository bucketRepository;

  @Autowired
  private mo.gov.dsscu.dss3.repository.ObjectVersionRepository objectVersionRepository;

  @Autowired
  private mo.gov.dsscu.dss3.repository.S3ObjectRepository s3ObjectRepository;

  public Bucket createBucket(String bucketName) {
    Bucket bucket = new Bucket();
    bucket.setName(bucketName);
    bucketRepository.save(bucket);
    Path folderPath = Paths.get(FOLDER + "/" + bucketName);
    try {
      Files.createDirectories(folderPath);
      System.out.println("Folder created or already exists: " + folderPath.toAbsolutePath());
    } catch (IOException e) {
      System.err.println("Failed to create folder: " + e.getMessage());
    }
    return bucket;
  }

  @Transactional
  public S3Object upsertObject(Bucket bucket, String key, String contentType, byte[] data) {
    Optional<S3Object> existing = s3ObjectRepository
        .findById(new ObjectId(bucket.getName(), key));
    S3Object s3Object;
    if (existing.isPresent()) {
      s3Object = existing.get();
      ObjectVersion version = s3Object.toObjectVersion();
      objectVersionRepository.save(version);
      s3Object.setVersion(s3Object.getVersion() + 1);
      s3ObjectRepository.save(s3Object);
    } else {
      s3Object = new S3Object();
      s3Object.setBucketName(bucket.getName());
      s3Object.setObjectKey(key);
      s3Object.setVersion(0);
      s3Object.setProvider("disk");
    }
    s3Object.setSize((long) data.length);
    s3Object.setContentType(contentType);
    String filePath = FOLDER + "/" + s3Object.getBucketName() + "/" + s3Object.getObjectKey().replaceAll("/", "_")
        + ".v" + s3Object.getVersion();
    s3Object.setPath(filePath);
    s3Object.write(data);
    s3ObjectRepository.save(s3Object);
    return s3Object;
  }
}
