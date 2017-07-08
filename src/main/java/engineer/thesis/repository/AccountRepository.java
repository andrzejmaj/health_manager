package engineer.thesis.repository;

import engineer.thesis.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUser_Id(Long id);

    Account findByPersonalDetail_Pesel(String pesel);

}
