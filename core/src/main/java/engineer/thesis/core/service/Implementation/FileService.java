package engineer.thesis.core.service.Implementation;

import engineer.thesis.core.service.Interface.IFileService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService implements IFileService {

    private String IMAGES_PATH = "/Users/andrzejmaj123/Documents/health_manager/core/src/main/resources/images/";

    @Override
    public FileSystemResource findProfilePicture(Long id) {
        File file = new File(IMAGES_PATH + id + ".png");
        return new FileSystemResource(file);
    }

    @Override
    public String saveProfilePicture(Long id, MultipartFile userImage) {

        Path path = Paths.get(IMAGES_PATH + id + ".png");

        if (userImage != null && !userImage.isEmpty()) {
            try {
                userImage.transferTo(new File(path.toString()));
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Saving profile picture was not successful", e);
            }
        }

        return path.toString();
    }
}
