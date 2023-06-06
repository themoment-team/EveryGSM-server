package com.themoment.everygsm.domain.email.service;

import com.themoment.everygsm.domain.email.dto.request.EmailSendDto;
import com.themoment.everygsm.domain.email.entity.EmailAuth;
import com.themoment.everygsm.domain.email.repository.EmailAuthRepository;
import com.themoment.everygsm.global.exception.CustomException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Random;

@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailService {

    private final EmailAuthRepository emailAuthRepository;
    private final JavaMailSender javaMailSender;

    @Async
    @Transactional(rollbackFor = Exception.class)
    public void sendEmail(EmailSendDto emailSendDto) {
        Random random = new Random();
        String authKey = String.valueOf(random.nextInt(8888) + 1111);

        sendAuthEmail(emailSendDto.getEmail(), authKey);
    }

    private void sendAuthEmail(String email, String authKey) {
        String subject = "EveryGSM 인증번호";
        String text = "EveryGSM 인증을 위한 인증번호는 <strong>" + authKey + "<strong /> 입니다. <br />";
        EmailAuth emailAuthEntity = emailAuthRepository.findById(email)
                .orElse(EmailAuth.builder()
                        .authentication(false)
                        .randomValue(authKey)
                        .attemptCount(0)
                        .email(email)
                        .build());
        if (emailAuthEntity.getAttemptCount() >= 10) {
            throw new CustomException("발송 횟수를 초과하였습니다", HttpStatus.BAD_REQUEST);
        }

        emailAuthEntity.updateRandomValue(authKey);
        emailAuthEntity.increaseAttemptCount();

        emailAuthRepository.save(emailAuthEntity);
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text,true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new CustomException("메일 발송에 실패했습니다", HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void checkEmail(String email , String authKey) {
        EmailAuth emailAuthEntity = emailAuthRepository.findById(email)
                .orElseThrow(()-> new CustomException("유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));
        checkAuthKey(emailAuthEntity,authKey);
        emailAuthEntity.updateAuthentication(true);
        emailAuthRepository.save(emailAuthEntity);
    }

    private void checkAuthKey(EmailAuth emailAuthEntity, String authKey) {
        if(!Objects.equals(emailAuthEntity.getRandomValue(), authKey)){
            throw new CustomException("인증번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}
