package shop.mtcoding.blog.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ThumbnailParser {
    public static String thumbnailParser(String html) {
        String img = "";
        Document doc = Jsoup.parse(html);
        Elements els = doc.select("img");
        if (els.size() == 0) {
            // 임시 사진 제공해주기
            String temp = "/images/dora.png";
            return temp;
        } else {
            Element el = els.get(0);
            img = el.attr("src");
            return img;
        }
    }
}
