import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileController {

    // 생성된 맵의 내용을 파일에 작성하는 부분
    public static void saveResultToTxt(HashMap<String, Integer> map) throws IOException {
        BufferedOutputStream bs = null;
        String schoolNameAndCount;
        try {
            bs = new BufferedOutputStream(new FileOutputStream("src/result.txt"));
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                //ㅇㅇ중학교\t192\n
                schoolNameAndCount = entry.getKey() + "\t" + entry.getValue() + "\n";
                bs.write(schoolNameAndCount.getBytes());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bs.close();
        }
    }
}
