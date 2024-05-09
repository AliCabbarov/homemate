package divacademy.homemate.service.impl;

import divacademy.homemate.model.dto.response.MessageResponse;
import divacademy.homemate.model.entity.Advert;
import divacademy.homemate.model.entity.File;
import divacademy.homemate.model.exception.ApplicationException;
import divacademy.homemate.repository.FileRepository;
import divacademy.homemate.service.AdvertService;
import divacademy.homemate.service.FileService;
import divacademy.homemate.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static divacademy.homemate.model.enums.Messages.MESSAGE_SUCCESSFULLY;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;
    private final AdvertService advertService;
    private final MessageUtil messageUtil;
    private final String PROTOCOL = "HTTP://";
    @Value(value = "${app.host.ip}")
    private String SERVER_ADDRESS;
    @Value(value = "${app.host.port}")
    private String SERVER_PORT;
    private final String URI = "/api/v1/files/download/";

    @Override
    @SneakyThrows
    public ResponseEntity<MessageResponse> uploadFile(MultipartFile file, Long id) {
        Advert advert = advertService.findById(id);

        Path newPath = Paths.get("adverts\\" + id);

        createDirectories(newPath);
        String newFileName = getFileName(file);

        File saveFile = buildFile(newFileName, advert);

        fileRepository.save(saveFile);

        Files.write(newPath.resolve(newFileName), file.getBytes());

        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage(MESSAGE_SUCCESSFULLY.getMessage()), MESSAGE_SUCCESSFULLY.getStatus()));
    }

    @Override
    public ResponseEntity<List<String>> getFilesByProductId(Long id) {
        Advert advert = advertService.findById(id);
        List<File> filesPath = fileRepository.findAllByProduct(advert);

        List<String> filesUris = getFilesUris(filesPath, id);
        return ResponseEntity.ok(filesUris);

    }

    @Override
    @SneakyThrows
    public ResponseEntity<Resource> download(String fileName) {
        try {

            List<String> idAndName = getIdAndName(fileName);
            Resource resource = new UrlResource(Path.of("adverts\\" + idAndName.get(0)).toUri().resolve(idAndName.get(1)));

            return new ResponseEntity<>(resource, buildHeaders(fileName), HttpStatus.OK);
        } catch (RuntimeException e) {
            throw new FileNotFoundException();
        }
    }


    @SneakyThrows
    public void createDirectories(Path path) {
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
    }

    private String createFileName(String format) {

        return UUID.randomUUID().toString().substring(0, 4) +
                "." +
                format;
    }

    public String getFileName(MultipartFile file) {
        String fileOriginal = file.getOriginalFilename();
        assert fileOriginal != null;
        String[] split = fileOriginal.split("\\.");
        String format = split[1];

        return createFileName(format);
    }

    public File buildFile(String newFileName, Advert advert) {
        return File.builder()
                .filePath(newFileName)
                .product(advert)
                .build();
    }

    @SneakyThrows
    public List<String> getFilesUris(List<File> files, Long id) {
        try {
            List<String> paths = new ArrayList<>(files.size());
            files
                    .forEach(file ->
                            paths.add(PROTOCOL +
                                    SERVER_ADDRESS + ":" +
                                    SERVER_PORT +
                                    URI +
                                    id + "_" +
                                    file.getFilePath()));

            return paths;
        } catch (RuntimeException e) {
            throw new FileNotFoundException();
        }
    }

    public List<String> getIdAndName(String fileName) {
        String[] split = fileName.split("_");
        String id = split[0];
        String name = split[1];
        return List.of(id, name);


    }

    public HttpHeaders buildHeaders(String fileName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", fileName);

        return httpHeaders;
    }

}
