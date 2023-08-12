package com.likelion.nsu.gojisik.service;

import com.likelion.nsu.gojisik.domain.Question;
import com.likelion.nsu.gojisik.domain.User;
import com.likelion.nsu.gojisik.dto.RequestDto;
import com.likelion.nsu.gojisik.dto.ResponseDto;
import com.likelion.nsu.gojisik.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService { //TODO - 테스트케이스 작성

    private final QuestionRepository questionRepository;


    @Transactional //질문 등록
    public Long saveQuestion(RequestDto requestDto) {
        return questionRepository.save(requestDto.toRequestEntity()).getId();
    }

    @Transactional //질문 리스트 조회
    public List<ResponseDto> getResponseDto() {
        List<Question> questionList = questionRepository.findAll();
        List<ResponseDto> responseDtoList = new ArrayList<>();

        for (Question question : questionList) {
            ResponseDto responseDto = ResponseDto.builder()
                    .id(question.getId())
                    .title(question.getTitle())
                    .category(question.getCategory())
                    .contents(question.getContents())
                    .createdDate(question.getCreatedDate())
                    .hits(question.getHits())
                    .user(question.getUser())
                    .build();
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    @Transactional //질문 조회
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Transactional
    public List<Question> getMyQuestionList(Long userId) {
        return questionRepository.findByUserId(userId);
    }
}

