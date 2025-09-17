package com.codepresso.codepresso.service.member;

import com.codepresso.codepresso.entity.member.Member;
import com.codepresso.codepresso.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 회원 도메인 서비스.
 * - 중복 체크(accountId/nickname/email)
 * - 회원가입: 비밀번호를 BCrypt로 암호화 후 저장, 유니크 제약 처리
 */
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 아이디 중복 여부 확인
     */
    public boolean isAccountIdDuplicate(String accountId) {
        return memberRepository.existsByAccountId(accountId);
    }

    /**
     * 닉네임 중복 여부 확인
     */
    public boolean isNicknameDuplicate(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    /**
     * 이메일 중복 여부 확인
     */
    public boolean isEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    /**
     * 회원가입 처리
     */
    @Transactional
    public Member register(String accountId, String rawPassword, String nickname, String email) {
        if (isAccountIdDuplicate(accountId)) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (isNicknameDuplicate(nickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }
        if (isEmailDuplicate(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        String encoded = passwordEncoder.encode(rawPassword);
        Member member = Member.builder()
                .accountId(accountId)
                .password(encoded)
                .nickname(nickname)
                .email(email)
                .build();
        try {
            return memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            // 동시성으로 인한 유니크 제약 위반 커버 (선행 중복 체크를 통과했지만 저장 시점에 충돌)
            throw new IllegalArgumentException("중복된 정보가 존재합니다.");
        }
    }
}
