package com.likelion.nsu.gojisik.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.nsu.gojisik.dto.QuestionRequestDto;
import com.likelion.nsu.gojisik.repository.QuestionRepository;
import com.likelion.nsu.gojisik.service.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class FileControllerTest {
    @InjectMocks
    FileController fileController;

    @Mock
    QuestionRepository questionRepository;

    @Mock
    @Autowired
    FileService fileService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    MockMvc mockMvc;

    @Test
    void createTest() throws Exception{
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        QuestionRequestDto dto = new QuestionRequestDto("it", "It doesn't work", "what should i do?");

        List<MultipartFile> files = List.of(
                new MockMultipartFile("file", "image.jpeg", MediaType.IMAGE_PNG_VALUE, "image".getBytes()),
                new MockMultipartFile("file", "audio.mp3", MediaType.APPLICATION_OCTET_STREAM_VALUE, "audio".getBytes())
        );

        // Perform the file upload request
        mockMvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.POST,"/files/{question_id}", 1L)
                        .file("image", files.get(0).getBytes())
                        .file("audio", files.get(1).getBytes())
                        .file(new MockMultipartFile("dto","","application/json", objectMapper.writeValueAsString(dto).getBytes()))
                        .contentType("multipart/form-data")
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"));

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/files/{question_id}", 1302L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"));

    }
}