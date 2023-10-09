//package ohai.newslang.domain.entity.news;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Embeddable;
//
//@Embeddable
//@Getter @Setter
//@NoArgsConstructor
//public class News {
//    @Column(nullable = false, unique = true)
//    private String url;
//    private String mediaName;
//    private String categoryName;
////    private String writer;
//    private String title;
//    private String contents;
//    private String thumbnailImagePath;
//
//    @Builder
//    public News(String url, String mediaName, String categoryName, String title, String contents, String thumbnailImagePath) {
//        this.url = url;
//        this.mediaName = mediaName;
//        this.categoryName = categoryName;
////        this.writer = writer;
//        this.title = title;
//        this.contents = contents;
//        this.thumbnailImagePath = thumbnailImagePath;
//    }
//}
