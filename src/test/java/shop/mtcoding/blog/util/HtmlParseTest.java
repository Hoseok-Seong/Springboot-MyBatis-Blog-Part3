package shop.mtcoding.blog.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

public class HtmlParseTest {
    @Test
    public void jsoup_test1() throws Exception {
        System.out.println("=================================");
        Document doc = Jsoup.connect("https://en.wikipedia.org/").get();
        System.out.println(doc.title());
        System.out.println("=================================");
        Elements newsHeadLines = doc.select("#mp-itn b a");
        for (Element headline : newsHeadLines) {
            System.out.println(headline.attr("title"));
            System.out.println(headline.absUrl("href"));
        }
        // String url =
        // "https://search.naver.com/search.naver?sm=tab_hty.top&where=view&query=%EB%B6%80%EC%82%B0+%EB%A7%9B%EC%A7%91&oquery=%EB%82%A0%EC%94%A8&tqi=h%2BTKwsp0J14ssSe6%2F3wssssssZw-060189&mode=normal";
        // Document doc = Jsoup.connect(url).get();
        // Elements elements = doc.select("");
        // elements.stream().forEach((e) -> {
        // System.out.println(e.text());
        // });

    }

    @Test
    public void jsoup_test2() throws Exception {
        String html = "<p>1</p><p><img src=\"data:image/png;base64,iVBORw0KG\"></p>";
        Document doc = Jsoup.parse(html);
        // System.out.println(doc);
        Elements els = doc.select("img");
        System.out.println(els);
        if (els.size() == 0) {
            // 임시 사진 제공해주기
            // db thumbnail -> /images/profile.jfif
            System.out.println("임시파일");
        } else {
            Element el = els.get(0);
            String img = el.attr("src");
            System.out.println(img);
            // db thumbnail -> img
        }
    }

    @Test
    public void parse_test1() {
        String html = "<p>1</p><p><img src=\"data:image/png;base64,iVBORw0KG\"></p>";
        String tag = parseEL(html, "img");
        System.out.println(tag);
        String attr = parseAttr(tag, "src");
        System.out.println(attr);
    }

    private String parseEL(String html, String tag) {
        String s1 = html.substring(html.indexOf(tag) - 1);
        return s1.substring(0, s1.indexOf(">") + 1);
    }

    private String parseAttr(String el, String attr) {
        String s1 = el.substring(el.indexOf(attr));

        int begin = s1.indexOf("\"");
        int end = s1.indexOf("\"", 2);

        return s1.substring(begin + 1, end);
    }

}