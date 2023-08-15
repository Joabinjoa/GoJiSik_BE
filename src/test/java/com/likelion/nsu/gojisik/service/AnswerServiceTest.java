package com.likelion.nsu.gojisik.service;

import com.likelion.nsu.gojisik.domain.Answer;
import com.likelion.nsu.gojisik.domain.Member;
import com.likelion.nsu.gojisik.domain.Question;
import com.likelion.nsu.gojisik.dto.AnswerRequestDto;
import com.likelion.nsu.gojisik.repository.AnswerRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
class AnswerServiceTest {
    private final EntityManager em;
    private final AnswerRepository answerRepository;
    private final AnswerService answerService;

    @Autowired
    public AnswerServiceTest(EntityManager em, AnswerRepository answerRepository, AnswerService answerService){
        this.em = em;
        this.answerRepository = answerRepository;
        this.answerService = answerService;
    }

    @Test
    void create() {
        // given
        Member member = new Member();
        em.persist(member);

        Question question = new Question();
        em.persist(question);

        AnswerRequestDto dto = new AnswerRequestDto("123");

        // when
        Long savedId = answerService.create(member.getId(), question.getId(), dto);

        // then
        Long answerId = answerRepository.findById(savedId).get().getId();
        Assertions.assertEquals(savedId, answerId);
    }

    @Test
    void adopt() {
        // given
        Member member = new Member();
        em.persist(member);

        Question question = new Question();
        em.persist(question);

        AnswerRequestDto dto = new AnswerRequestDto("123");
        Long savedId = answerService.create(member.getId(), question.getId(), dto);

        // when
        Long adoptedId = answerService.adopt(savedId);

        // then
        Answer answer = answerRepository.findById(adoptedId).get();
        Assertions.assertEquals(Boolean.TRUE, answer.getIsAdopted());
    }

    @Test
    void findAnswers() {
        // given
        Member member = new Member();
        em.persist(member);

        Question question = new Question();
        em.persist(question);

        AnswerRequestDto dto = new AnswerRequestDto("123");
        answerService.create(member.getId(), question.getId(), dto);
        AnswerRequestDto dto2 = new AnswerRequestDto("123123");
        answerService.create(member.getId(), question.getId(), dto2);
        AnswerRequestDto dto3 = new AnswerRequestDto("123123123");
        answerService.create(member.getId(), question.getId(), dto3);

        // when
        List<Answer> answerList = answerService.findAnswers(question.getId());

        // then
        Assertions.assertEquals(3, answerList.size());
    }

    @Test
    void findByUserId() {
        // given
        Member member = new Member();
        em.persist(member);
        Member member2 = new Member();
        em.persist(member2);

        Question question = new Question();
        em.persist(question);

        AnswerRequestDto dto = new AnswerRequestDto("123");
        answerService.create(member.getId(), question.getId(), dto);

        AnswerRequestDto dto2 = new AnswerRequestDto("123123");
        answerService.create(member2.getId(), question.getId(), dto2);
        AnswerRequestDto dto3 = new AnswerRequestDto("123123123");
        answerService.create(member2.getId(), question.getId(), dto3);

        // when
        List<Answer> answerList = answerService.findByUserId(member.getId());
        List<Answer> answerList2 = answerService.findByUserId(member2.getId());

        // then
        Assertions.assertEquals(1, answerList.size());
        Assertions.assertEquals(2, answerList2.size());
    }
}