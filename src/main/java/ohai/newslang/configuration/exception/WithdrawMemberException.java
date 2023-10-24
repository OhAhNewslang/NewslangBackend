package ohai.newslang.configuration.exception;

public class WithdrawMemberException extends RuntimeException{
    public WithdrawMemberException() {
        super("이미 탈퇴된 회원입니다.");
    }
}
