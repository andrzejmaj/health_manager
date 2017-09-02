package engineer.thesis.core.repository;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository {

    FileSystemResource findAccountPicture(Long id);

}
