package com.likelion.nsu.gojisik.handler;

import com.likelion.nsu.gojisik.domain.File;
import com.likelion.nsu.gojisik.domain.FileType;
import com.likelion.nsu.gojisik.domain.Question;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class FileHandler {
    private final List<String> extensions = new ArrayList<>(List.of("jpeg", "png", "gif", "mp4", "mp3", "m4a", "mpeg", "wav", "wma"));

    public List<File> parseFile(Question question, List<MultipartFile> multipartFiles) throws IOException {
        List<File> files = new ArrayList<>();

        if (multipartFiles.isEmpty()) {
            return files;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current_date = simpleDateFormat.format(new Date());

        String absolutePath = new java.io.File("").getAbsolutePath() + "\\";
        String path = "src/main/resources/static/file/" + current_date;
        java.io.File file = new java.io.File(path);

        if (!file.exists()) {
            file.mkdirs();
        }

        for (MultipartFile multipartFile : multipartFiles) {
            String[] originalFileExtension = {null};
            FileType fileType = null;
            if (!multipartFile.isEmpty()) {
                String contentType = multipartFile.getContentType();

                if (contentType != null) {
                    if (contentType.startsWith("image")) {
                        fileType = FileType.Image;
                    } else if (contentType.startsWith("audio") || contentType.startsWith("video")) {
                        fileType = FileType.Audio;
                    }
                }

                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    extensions.stream().forEach(fileExtension -> {
                                if (contentType.endsWith(fileExtension)) {
                                    originalFileExtension[0] = "." + fileExtension;
                                }});
                }
            }

            String newFileName = System.nanoTime() + originalFileExtension[0];

            File newFile = File.builder()
                    .type(fileType)
                    .originFileName(multipartFile.getOriginalFilename())
                    .savedFileName(path + "/" + newFileName)
                    .fileSize(multipartFile.getSize())
                    .question(question)
                    .build();

            files.add(newFile);

            file = new java.io.File(absolutePath + path + "/" + newFileName);
            multipartFile.transferTo(file);
        }

        return files;
    }
}
