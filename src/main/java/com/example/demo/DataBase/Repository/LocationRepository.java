package com.example.demo.DataBase.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.DataBase.Entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

	@Query(value = "SELECT * FROM tbl_location WHERE name LIKE :name ORDER BY length(name) , name LIMIT 10 ; " , nativeQuery = true)
	List<Location> getByNameLikeOrderByLenNameAndNameLimit10(@Param("name") String name);

	Optional<Location> getByBeaconKey(String beaconKey);

}
