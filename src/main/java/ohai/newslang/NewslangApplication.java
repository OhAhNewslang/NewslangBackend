package ohai.newslang;

import org.aspectj.lang.annotation.Before;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
// Scheduling 기능 활성화?
@EnableJpaAuditing
// 객체 생성 시간 = 컬럼 생성 시간(TimeStamp) 자동으로 찍어주는 Auditing 기능 활성화
@SpringBootApplication
public class NewslangApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewslangApplication.class, args);
	}

}
