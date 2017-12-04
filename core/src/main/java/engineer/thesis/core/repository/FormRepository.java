package engineer.thesis.core.repository;

import engineer.thesis.core.model.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {

    @Query("select f from Form as f join f.owner u where (f.active = true) and (lower(f.name) like concat('%',lower(:name),'%') ) and (u.id = :userId or u.role='ROLE_ADMIN' or u.role='ROLE_RECEPTIONIST')")
    List<Form> findByNameContainingIgnoreCaseAndActiveIsTrue(@Param("name") String name, @Param("userId") Long userId);

    @Query("select f from Form as f join f.owner u where (f.active = true) and (u.id = :userId or u.role='ROLE_ADMIN' or u.role='ROLE_RECEPTIONIST')")
    List<Form> findAvailableForms(@Param("userId") Long userId);

    Form findByIdAndActiveIsTrue(Long id);
}
