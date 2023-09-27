package ohai.newslang.service;

import ohai.newslang.domain.Member;
import ohai.newslang.domain.subscribe.item.Keyword;
import ohai.newslang.domain.subscribe.item.Media;
import ohai.newslang.repository.subscribe.SubscribeItemRepository;
import ohai.newslang.service.subscribe.MemberSubscribeItemService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberSubscribeServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    SubscribeItemRepository subscribeItemRepository;
    @Autowired
    MemberSubscribeItemService memberSubscribeItemService;

    @Test
    @Rollback(false)
    void Test(){
        Member member1 = createMember("김경민", "Path1", LocalDateTime.now());
        Member member2 = createMember("이진호","Path2", LocalDateTime.now());
        Member member3 = createMember("박수빈","Path3", LocalDateTime.now());

        Media media1 = createMedia("매일경제", "Path1");
        Media media2 = createMedia("조선일보", "Path2");
        Media media3 = createMedia("중앙일보", "Path3");
        Media media4 = createMedia("동아일보", "Path4");
        Media media5 = createMedia("TIMES", "Path5");
        Media media6 = createMedia("경향신문", "Path6");

        Keyword keyword1 = createKeyword("매일경제");
        Keyword keyword2 = createKeyword("조선일보");
        Keyword keyword3 = createKeyword("중앙일보");

        List<String> mediaNameList = new ArrayList<>();
        mediaNameList.add("매일경제");
        mediaNameList.add("조선일보");
        mediaNameList.add("중앙일보");
        try {
            memberSubscribeItemService.updateSubscribe(member1.getId(), mediaNameList, Media.class);
        } catch (Exception ex){

        }

        List<String> keywordList = new ArrayList<>();
        keywordList.add("매일경제");
        keywordList.add("조선일보");
        keywordList.add("중앙일보");
        try {
        memberSubscribeItemService.updateSubscribe(member1.getId(), keywordList, Keyword.class);
        } catch (Exception ex){

        }

        List<String> mediaNameList2 = new ArrayList<>();
        mediaNameList2.add("동아일보");
        mediaNameList2.add("TIMES");
        mediaNameList2.add("경향신문");
            try {
        memberSubscribeItemService.updateSubscribe(member2.getId(), mediaNameList2, Media.class);
            } catch (Exception ex){

            }


        List<String> mediaNameList3 = new ArrayList<>();
        mediaNameList3.add("동아일보");
        mediaNameList3.add("TIMES");
        mediaNameList3.add("경향신문");
                try {
        memberSubscribeItemService.updateSubscribe(1L, mediaNameList3, Media.class);
                } catch (Exception ex){

                }

//        List<Long> ids = subscribeItemRepository.findAllWithUrls();
//        System.out.println("asdf");
    }

    @Test
    @Rollback(false)
    void Test2(){
    }

//    private List<Long> getIdList(List<SubscribeItem> findSubscribeItemList, Class<?> entityType){
//        List<String> mediaNameList = findSubscribeItemList.stream()
//                .map(o -> o.getName())
//                .collect(Collectors.toList());
//        return subscribeItemRepository.findAllWithUrls(mediaNameList, entityType);
//    }

    private Member createMember(String name, String imagePath, LocalDateTime joinTime){
        Member member = new Member();
        member.setName(name);
        member.setImagePath(imagePath);
        member.setJoinDate(joinTime);
        em.persist(member);
        return member;
    }

    private Media createMedia(String name, String imagePath){
        Media media = new Media();
        media.setName(name);
        media.setImagePath(imagePath);
        em.persist(media);
        return media;
    }

    private Keyword createKeyword(String name){
        Keyword keyword = new Keyword();
        keyword.setName(name);
        em.persist(keyword);
        return keyword;
    }
}