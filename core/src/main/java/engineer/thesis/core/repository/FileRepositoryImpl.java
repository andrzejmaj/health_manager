package engineer.thesis.core.repository;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;

@Repository
public class FileRepositoryImpl implements FileRepository {

    private String IMAGES_DIRECTORY = "/Users/andrzejmaj123/Documents/health_manager/core/src/main/resources/images/";;
    private String FILES_DIRECTORY = "/Users/andrzejmaj123/Documents/health_manager/core/src/main/resources/images/";;

    @Override
    public FileSystemResource findUserProfilePicture(Long id) {
        return null;
    }
}
