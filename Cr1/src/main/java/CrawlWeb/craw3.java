package CrawlWeb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class craw3 {
    public void crawlStoriesByCategory(String categoryUrl) {
        try {
            // Kết nối đến trang web thể loại truyện
            Document doc = Jsoup.connect(categoryUrl).get();
            Element page = doc.select("div[class=\"paging\"]").first();
            int countPage =Integer.parseInt(page.ownText().split("/")[1]);
            for(int i = 1; i < countPage; i++){
                Document docPage = Jsoup.connect(categoryUrl+"page-"+i).timeout(5000).get();
                Elements elementPage = docPage.select("span[class=\"curent\"]");
                for(int j = 0; j < elementPage.size();j++){
                    System.out.println(elementPage.get(j).ownText());
                }

            }

            // Lấy tất cả các truyện trong thể loại
            Elements link = doc.select("div[class=\"main-content\"] [class=\"news-item\"] ");
            Elements linkk = doc.select("div[class=\"title\"]");
            String danhMuc = linkk.select("a").text();
            System.out.println("Danh Mục: " + danhMuc);
            for(Element link1 : link) {

                String tenTruyen = link1.select("a").attr("title");
                String dateView = link1.select("p[class=\"info-post\"]").text();
                String content = link1.select("p[class=\"sapo\"]").text();

                Elements link2 = link1.select("div[class=\"img\"]");
                for(Element link3 : link2){
                    String url = link3.select("a").attr("href");
                    String imgUrl = link3.select("img").attr("src");

                    //           System.out.println("Danh Mục: " + danhMuc);
                    System.out.println("Image: " + imgUrl);
                    System.out.println("Tên Truyện: " + tenTruyen);
                    System.out.println("URL: " + url);
                    System.out.println(dateView);
                    System.out.println("Mô tả:  " + content );

                    System.out.println("--------------------------------------------------------");
                }
            }
        }catch (Exception e){
            e.getMessage();
        }
    }
    public static void main(String[] args) {
        craw3 crawler = new craw3();
        String category1Url = "https://cotich.net/co-tich-viet-nam.html";
        crawler.crawlStoriesByCategory(category1Url);

        String category2Url = "https://cotich.net/co-tich-ve-loai-vat.html";
        crawler.crawlStoriesByCategory(category2Url);

    }
}
