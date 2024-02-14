package onelky.chatapp.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    @Value("${api.cloudinary.folder_name}")
    private String FOLDER_NAME;
    private final Cloudinary cloudinary;


    public HashMap<String, String> uploadFile(MultipartFile multipartFile, String existingPictureId) throws IOException {

        if (existingPictureId != null) {
            cloudinary.uploader().destroy(existingPictureId, null);
        }

        Map<?,?> cloudinaryObj = cloudinary
                .uploader()
                .upload(multipartFile.getBytes(), ObjectUtils.asMap(
                        "public_id", UUID.randomUUID().toString(),
                        "folder", FOLDER_NAME
                ));

        return new HashMap<>() {{
            put("url", (String) cloudinaryObj.get("url"));
            put("publicId", (String) cloudinaryObj.get("public_id"));
        }};
    }

}
