package vn.com.viettel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.viettel.entity.ShowcaseEntity;

import java.util.List;

@Repository
public interface ShowcaseRepository extends JpaRepository<ShowcaseEntity, Long> {

    @Query(nativeQuery = true, value = "Select * from showcase where is_del = false ORDER BY id")
    List<ShowcaseEntity> findAllShowcase();

}
