package CrawlWeb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class craw2 {
    public static void main(String[] args) {
        String baseUrl = "https://cotich.net";

        try {
            // Kết nối đến trang chính của cotich.net
            Document mainPage = Jsoup.connect(baseUrl).get();
            Elements categories = mainPage.select("ul.menu > li > a");

            // Lặp qua từng danh mục
            for (Element category : categories) {
                String categoryUrl = baseUrl + category.attr("href");
                String categoryName = category.text();

                System.out.println("Danh mục: " + categoryName);

                // Kết nối đến trang của từng danh mục
                Document categoryPage = Jsoup.connect(categoryUrl).get();

                // Lấy các trang trong phân trang
                Elements paging = categoryPage.select("div.paging a");
                if (paging.isEmpty()) {
                    System.out.println("Không tìm thấy phân trang cho danh mục này.");
                    continue;
                }

                // Lặp qua các liên kết phân trang
                for (Element page : paging) {
                    String pageUrl = baseUrl + page.attr("href");
                    Document pageDoc = Jsoup.connect(pageUrl).get();

                    // Lấy các truyện trong từng trang
                    Elements stories = pageDoc.select("div.news-item");

                    // Lặp qua từng truyện trong từng trang
                    for (Element story : stories) {
                        String storyTitle = story.select("a").attr("title");
                        String storyUrl = baseUrl + story.select("a").attr("href");
                        String storyDateView = story.select("p.info-post").text();
                        String storyContent = story.select("p.sapo").text();
                        String storyImage = story.select("div.img img").attr("src");

                        System.out.println("Tiêu đề: " + storyTitle);
                        System.out.println("URL: " + storyUrl);
                        System.out.println("Ngày/Xem: " + storyDateView);
                        System.out.println("Nội dung: " + storyContent);
                        System.out.println("Ảnh: " + storyImage);
                        System.out.println("--------------------------------------");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
