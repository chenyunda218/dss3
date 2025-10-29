package mo.gov.dsscu.dss3.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import mo.gov.dsscu.dss3.controller.model.ListAllMyBucketsResult;
import mo.gov.dsscu.dss3.controller.model.Owner;
import mo.gov.dsscu.dss3.model.Bucket;
import mo.gov.dsscu.dss3.model.S3Object;
import mo.gov.dsscu.dss3.repository.BucketRepository;
import mo.gov.dsscu.dss3.repository.S3ObjectRepository;
import mo.gov.dsscu.dss3.service.BucketService;

@RestController
public class S3Controller {

  @Autowired
  private BucketRepository bucketRepository;

  @Autowired
  private BucketService bucketService;

  @Autowired
  private S3ObjectRepository s3ObjectRepository;

  @PutMapping(value = "/{bucketName}/")
  public ResponseEntity<Object> createBucketName(@PathVariable("bucketName") String bucketName) {
    Optional<Bucket> existingBucket = bucketRepository.findByName(bucketName);
    if (existingBucket.isPresent()) {
      return ResponseEntity.status(409).body("Bucket already exists");
    }
    Bucket bucket = bucketService.createBucket(bucketName);
    mo.gov.dsscu.dss3.controller.model.Bucket responseBucket = new mo.gov.dsscu.dss3.controller.model.Bucket();
    responseBucket.setName(bucket.getName());
    return ResponseEntity.ok().header("x-amz-bucket-arn", "dsscu").header("Location", "/" + bucket.getName())
        .body(responseBucket);
  }

  @GetMapping(value = "/", produces = MediaType.APPLICATION_XML_VALUE)
  public ListAllMyBucketsResult listBuckets() {
    ListAllMyBucketsResult result = new ListAllMyBucketsResult();
    result.setOwner(new Owner("dsscu", "0"));
    for (Bucket bucket : bucketRepository.findAll()) {
      result.getBuckets().add(mo.gov.dsscu.dss3.controller.model.Bucket.of(bucket));
    }
    return result;
  }

  @GetMapping("/{bucketName}/{*key}")
  public ResponseEntity<byte[]> getObject(@PathVariable("bucketName") String bucketName,
      @PathVariable("key") String key) {
    key = trimKey(key);
    Optional<S3Object> obj = s3ObjectRepository.findByBucketNameAndObjectKey(bucketName, key);
    if (obj.isEmpty()) {
      return ResponseEntity.status(404).body(null);
    }
    try {
      S3Object s3Object = obj.get();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentLength(s3Object.getSize());
      headers.setContentType(MediaType.valueOf(s3Object.getContentType()));
      return ResponseEntity.ok().headers(headers).body(s3Object.read());
    } catch (Exception e) {
      return ResponseEntity.status(500).body(null);
    }
  }

  @PutMapping("/{bucketName}/{*key}")
  public ResponseEntity<?> upsertObject(@RequestBody byte[] body, @PathVariable("bucketName") String bucketName,
      @PathVariable("key") String key, @RequestHeader("Content-Type") String contentType) {
    Optional<Bucket> existingBucket = bucketRepository.findByName(bucketName);
    if (existingBucket.isEmpty()) {
      return ResponseEntity.status(400).body("Bucket does not exist");
    }
    Bucket bucket = existingBucket.get();
    key = trimKey(key);
    bucketService.upsertObject(bucket, key, contentType, body);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{bucketName}")
  public void deleteBucket(@PathVariable("bucketName") String bucketName) {

  }

  private String trimKey(String key) {
    key = key.startsWith("/") ? key.substring(1) : key;
    key = key.endsWith("/") ? key.substring(0, key.length() - 1) : key;
    return key;
  }

}
