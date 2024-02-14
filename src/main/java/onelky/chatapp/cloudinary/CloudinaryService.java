package onelky.chatapp.cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

public interface CloudinaryService {
    HashMap<String, String> uploadFile(MultipartFile multipartFile, String existingPictureId) throws IOException;
}
