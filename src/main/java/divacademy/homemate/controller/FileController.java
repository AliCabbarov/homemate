package divacademy.homemate.controller;

import divacademy.homemate.aop.Log;
import divacademy.homemate.model.dto.response.MessageResponse;
import divacademy.homemate.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("api/v1/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/{advertId}")
    @Log
    public ResponseEntity<MessageResponse> uploadFile(@RequestPart(value = "file") MultipartFile file,
                                                      @PathVariable() Long advertId) {
        return fileService.uploadFile(file, advertId);
    }

    @Log
    @GetMapping("/{id}")
    public ResponseEntity<List<String>> getFilesByAdvertId(@PathVariable("id") Long id) throws FileNotFoundException {
        return fileService.getFilesByProductId(id);
    }

    @Log
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable("fileName") String fileName) {
        return fileService.download(fileName);
    }

}
