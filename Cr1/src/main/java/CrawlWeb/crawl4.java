package CrawlWeb;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class crawl4 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/crawl";  // Thay thế bằng URL của cơ sở dữ liệu MySQL
        String user = "root";  // Thay thế bằng tên người dùng của cơ sở dữ liệu
        String password = "123456";  // Thay thế bằng mật khẩu của cơ sở dữ liệu
        String sql = "INSERT INTO stories (tenTruyen, dateView, content, url, imgUrl) VALUES (?, ?, ?, ?, ?)";

        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            Document doc = Jsoup.connect("https://cotich.net/co-tich-viet-nam.html").get();
            Elements link = doc.select("div.main-content .news-item");

            for (Element element : link) {
                String tenTruyen = element.select("a").attr("title");
                String dateView = element.select("p.info-post").text();
                String content = element.select("p.sapo").text();

                Elements imgElement = element.select("div.img");
                for (Element img : imgElement) {
                    String urlStory = img.select("a").attr("href");
                    String imgUrl = img.select("img").attr("src");

                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, tenTruyen);
                        pstmt.setString(2, dateView);
                        pstmt.setString(3, content);
                        pstmt.setString(4, urlStory);
                        pstmt.setString(5, imgUrl);
                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
