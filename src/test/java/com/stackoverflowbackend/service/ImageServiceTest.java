package com.stackoverflowbackend.service;

import com.stackoverflowbackend.exceptions.AnswerNotFoundException;
import com.stackoverflowbackend.models.Answer;
import com.stackoverflowbackend.models.Image;
import com.stackoverflowbackend.repositories.AnswerRepository;
import com.stackoverflowbackend.repositories.ImageRepository;
import com.stackoverflowbackend.services.user.ImageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    ImageRepository imageRepository;

    @Mock
    AnswerRepository answerRepository;

    @InjectMocks
    ImageServiceImpl imageService;

    @Test
    void testStoreFileSuccess() {
        Long answerId = 1L;
        Answer answer = new Answer();
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "Spring Framework".getBytes());

        given(answerRepository.findById(answerId)).willReturn(Optional.of(answer));

        imageService.storeFile(multipartFile, answerId);

        verify(answerRepository, times(1)).findById(answerId);
        verify(imageRepository, times(1)).save(any(Image.class));
    }

    @Test
    void testStoreFileNotFoundId() {
        Long answerId = 1L;
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "Spring Framework".getBytes());

        given(answerRepository.findById(answerId)).willReturn(Optional.empty());

        assertThrows(AnswerNotFoundException.class,
                () -> imageService.storeFile(multipartFile, answerId));
    }
}