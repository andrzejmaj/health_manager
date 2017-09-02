package engineer.thesis.core.service.Interface;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    FileSystemResource findProfilePicture(Long id);

    String saveProfilePicture(Long id, MultipartFile userImage);
}
