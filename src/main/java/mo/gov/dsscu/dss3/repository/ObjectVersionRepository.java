package mo.gov.dsscu.dss3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mo.gov.dsscu.dss3.model.ObjectId;
import mo.gov.dsscu.dss3.model.ObjectVersion;

public interface ObjectVersionRepository extends JpaRepository<ObjectVersion, ObjectId> {

}
