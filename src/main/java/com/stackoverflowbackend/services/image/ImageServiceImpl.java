package com.stackoverflowbackend.services.image;

import com.stackoverflowbackend.exceptions.ObjectNotFoundException;
import com.stackoverflowbackend.models.Answer;
import com.stackoverflowbackend.models.Image;
import com.stackoverflowbackend.repositories.AnswerRepository;
import com.stackoverflowbackend.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final AnswerRepository answerRepository;

    @SneakyThrows
    @Override
    public void storeFile(MultipartFile multipartFile, Long answerId) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new ObjectNotFoundException("answer", answerId));

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        Image image = Image.builder()
                .name(fileName)
                .answer(answer)
                .type(multipartFile.getContentType())
                .data(multipartFile.getBytes())
                .build();

        imageRepository.save(image);
    }
}
