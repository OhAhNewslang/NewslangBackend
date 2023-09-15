package ohai.newslang;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class NewslangApplicationTests {

//	@Autowired
//	MemberRepository memberRepository;
//	@Autowired
//	SubscribeMediaRepository subscribeMediaRepository;

	@Test
	@Rollback(false)
	void contextLoads() {
//		Member m1 = new Member();
//		m1.setImagePath("ABCD");
//		m1.setJoinDate(LocalDateTime.now());
//		Member m2 = new Member();
//		m2.setImagePath("FFFF");
//		m2.setJoinDate(LocalDateTime.now());
//		memberRepository.save(m1);
//		memberRepository.save(m2);
//
//		Media media1 = new Media();
//		media1.setName("동아일보");
//		media1.setImagePath("A_PATH");
//		Media media2 = new Media();
//		media2.setName("매일경제");
//		media2.setImagePath("B_PATH");
//		Media media3 = new Media();
//		media3.setName("TIMES");
//		media3.setImagePath("C_PATH");
//		Media media4 = new Media();
//		media4.setName("조선일보");
//		media4.setImagePath("D_PATH");
//		Media media5 = new Media();
//		media5.setName("중앙일보");
//		media5.setImagePath("E_PATH");
//
//		SubscribeMedia sm1 = new SubscribeMedia();
//		sm1.setMember(m1);
//		sm1.setMedia(media1);
//		SubscribeMedia sm2 = new SubscribeMedia();
//		sm2.setMember(m1);
//		sm2.setMedia(media2);
//		SubscribeMedia sm3 = new SubscribeMedia();
//		sm3.setMember(m1);
//		sm3.setMedia(media3);
//
//		SubscribeMedia sm4 = new SubscribeMedia();
//		sm4.setMember(m2);
//		sm4.setMedia(media4);
//		SubscribeMedia sm5 = new SubscribeMedia();
//		sm5.setMember(m2);
//		sm5.setMedia(media5);
//
//		subscribeMediaRepository.save(sm1);
//		subscribeMediaRepository.save(sm2);
//		subscribeMediaRepository.save(sm3);
//		subscribeMediaRepository.save(sm4);
//		subscribeMediaRepository.save(sm5);
	}
}
