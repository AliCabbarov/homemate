package divacademy.homemate.service;

import divacademy.homemate.model.dto.response.MessageResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileService {
    ResponseEntity<MessageResponse> uploadFile(MultipartFile file, Long id);

    ResponseEntity<List<String>> getFilesByProductId(Long id) throws FileNotFoundException;

    ResponseEntity<Resource> download(String fileName);
}
