package mo.gov.dsscu.dss3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mo.gov.dsscu.dss3.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
