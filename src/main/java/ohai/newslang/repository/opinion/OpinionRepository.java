package ohai.newslang.repository.opinion;

import ohai.newslang.domain.entity.opinion.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OpinionRepository extends JpaRepository<Opinion, Long> {
    @Query("SELECT o FROM Opinion o WHERE o.id =:oId")
    Opinion findNoOptionalById(@Param("oId") Long oId);
}
