//package temp;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class Scrapper {
//
//    private ArrayList<String> schoolURL = new ArrayList<>();
//
////    private String elem = "http://www.studyholic.com/ele/School_TotalList_ele.asp?page=";
////    private String mid = "http://www.studyholic.com/mid/School_TotalList_mid.asp?page=";
////    private String high = "http://www.studyholic.com/high/School_TotalList_high.asp?page=";
////    private String univ = "http://www.studyholic.com/uni/UniTotal_List.asp?page=";
//
//
//    public Scrapper() {
//        setSchoolURL();
//    }
//
//    public void setSchoolURL() {
//        schoolURL.add("http://www.studyholic.com/ele/School_TotalList_ele.asp?page=");      // 초등학교
//        schoolURL.add("http://www.studyholic.com/mid/School_TotalList_mid.asp?page=");      // 중학교
//        schoolURL.add("http://www.studyholic.com/high/School_TotalList_high.asp?page=");    // 고등학교
//        schoolURL.add("http://www.studyholic.com/uni/UniTotal_List.asp?page=");             // 대학교
//    }
//
//    public void generate_schoolMap(HashMap<String, Integer> map) throws IOException {
//
//        for(int i=0; i<schoolURL.size()-1; i++) {
//            String baseURL = schoolURL.get(i);
//            int page = 1;
//            while(true) {
//                String url = baseURL + Integer.toString(page++);
//                Document doc = Jsoup.connect(url).get();
//                for (Element table : doc.select("table[id=tbl_list]")) {
//                    Elements rows = table.select("tbody > tr > td > a > b");
//                    if (rows.size() == 0) {
//                        System.out.println("All schools are added");
//                        break;
//                    }
//                    for (Element row : rows) {
//                        System.out.println(row.text());
////                        map.put(row.text(), 0);
//                    }
//                }
//            }
//        }
//    }
//}
