import com.opencsv.CSVReader;
import java.io.*;
import java.util.HashMap;

/*
 * 1. 과제내용: 댓글리스트(첨부파일)에서 유효한 것으로 보이는 학교 이름을 최대한 찾아내고, 학교별로 카운트해주시길 바랍니다.
 *
 * 2. 문제에서 도출할 수 있는 내용들
 *  - 본 내용은 배달의민족에서 짜장면 제공 이벤트를 위해 가장 많은 댓글을 작성한 학교를 순서대로 선정하는 것으로 보임
 *  - 주어진 csv 파일은 한 row 당 한 사람이 쓴 댓글 하나를 의미하는 것으로 파악됨
 *  - 그러므로 한 row 당 일반적으로 하나의 학교가 작성되어 있어야 함 (그러나 문제를 보면 한 댓글에 2개 이상의 학교 이름이 보이는 경우도 존재)
 *  - 학교 이름을 명확하게 작성(~초등학교, ~중학교, ~고등학교, ~대학교)하는 경우도 있지만, 간략화 하는 경우(~초, ~중, ~고, ~대)도 존재함
 *  - 학교 이름 중 대학교부설고등학교가 있음
 *  - 학교 이름을 애매하게 띄어쓰는 경우도 존재 (안산 디자인 문화 고등학교?)
 *
 * 3. 확인해야 할 부분
 *  - 유효한 것으로 보이는 학교 이름은 무엇일까? -> 하나의 댓글에 여러번 적는다고 해서 그것이 모두 카운트 되는가? -> 아니라고 생각
 *  - 여러 댓글에 동일한 학교 이름이 적혀있다면? -> 카운트 된다고 생각
 *  - 카뱅중학교, 카뱅중은 동일한가? -> 동일하다고 판단하는게 맞다고 생각
 */

public class Main {

    // 전체 학교의 정보를 담는 해시맵(학교이름, 호출된 카운트)
    static HashMap<String, Integer> map = new HashMap<>();

    public static void main(String[] args) throws IOException {

        CSVReader       csvReader       = new CSVReader(new FileReader("src/comments.csv"));    // 제공 파일
        for (String[] line : csvReader) {
            // System.out.println("[" + i++ + "]");
            // 불러온 csv 파일을 각 row 별로 읽어온다.
            SchoolFinder.find(line, map);
        }

//        int count = 0;
//        for (Map.Entry<String, Integer> entry : map.entrySet()) {
//            System.out.println("[Key]:" + entry.getKey() + " [Value]:" + entry.getValue());
//            count += entry.getValue();
//        }
//        System.out.println("school count : " + map.size() + " , total Count : " + count);
        FileController.saveResultToTxt(map);
    }
}
