package engineer.thesis.core.repository;

import engineer.thesis.core.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUser_Id(Long id);

    Account findByPersonalDetails_Pesel(String pesel);

}
