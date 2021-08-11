//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvException;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
///*
// * 1. 과제내용: 댓글리스트(첨부파일)에서 유효한 것으로 보이는 학교 이름을 최대한 찾아내고, 학교별로 카운트해주시길 바랍니다.
// *
// * 2. 문제에서 도출할 수 있는 내용들
// *  - 본 내용은 배달의민족에서 짜장면 제공 이벤트를 위해 가장 많은 댓글을 작성한 학교를 순서대로 선정하는 것으로 보임
// *  - 주어진 csv 파일은 한 row당 한 사람이 쓴 댓글 하나를 의미하는 것으로 파악됨
// *  - 그러므로 한 row당 일반적으로 하나의 학교가 작성되어 있어야 함 (그러나 문제를 보면 한 댓글에 2개 이상의 학교 이름이 보이는 경우도 존재)
// *  - 학교 이름을 명확하게 작성(~초등학교, ~중학교, ~고등학교, ~대학교)하는 경우도 있지만, 간략화 하는 경우(~초, ~중, ~고, ~대)도 존재함
// *  - 학교 이름 중 대학교부설고등학교가 있음
// *  - 학교 이름을 애매하게 띄어쓰는 경우도 존재 (안산 디자인 문화 고등학교?)
// *
// * 3. 확인해야 할 부분
// *  - 유효한 것으로 보이는 학교 이름은 무엇일까? -> 하나의 댓글에 여러번 적는다고 해서 그것이 모두 카운트 되는가? -> 아니라고 생각
// *  - 여러 댓글에 동일한 학교 이름이 적혀있다면? -> 카운트 된다고 생각
// *  - 카뱅중학교, 카뱅중은 동일한가? -> 동일하다고 판단하는게 맞다고 생각
// */
//
//public class Main {
//
//    // 전체 학교의 정보를 담는 해시맵(학교이름, 호출된 카운트)
//    static HashMap<String, Integer> map = new HashMap<>();
//
//    public static void main(String[] args) throws IOException, CsvException {
//
//        CSVReader csvReader = new CSVReader(new FileReader("src/comments.csv"));    // 제공 파일
//        int i = 1; // 테스트용
//        for (String[] line : csvReader) {
//            // 불러온 csv 파일을 각 row 별로 읽어온다.
//            System.out.println("[" + i++ + "] " + findSchoolName(line));
////            System.out.println("[" + i++ + "] " + String.join("", line));
//            findSchoolName(line);
//        }
//        saveResultToTxt(map);
//    }
//
//    public static String findSchoolName(String[] line) {
//
//        ArrayList<String> wordList = new ArrayList<>();
//
//        String[] schoolIndicators = {"초등학교" , "중학교" , "고등학교"};
//
//        // 한글을 제외한 모든 단어를 공란처리하고 데이터를 확인하는 것으로 결정
////        String message = String.join("", line);
//        String[] words = (String.join("", line)).split(",| |\\?|\\(|\\)|!|❤|♡|★|\\n|\\/|-|\\<|\\>|❣️|~|#|\\.|\\-|❗");
//
//        // 전체 이름(~학교)을 찾았으면 -> 간략화된 이름을 갖는 내용들은 무시해도 좋을 듯
//        boolean     findFullName = false;
//        boolean     findSimpleName = false;
//
//        // 결과 문자열을 저장하는 부분
//        String      result  = "";
//
//        int index = 0;
//        String name = "";
//        for (String word : words) {
//            word.replaceAll("우리" , "");
//            wordList.add(word);
//            // 1. 단어 중 간략화 된 학교 이름을 나타내는 '초,중,고'가 들어가 있는지 확인 (대학교는 따로 구분 -> 대학교 부설학교때문에)
//            if (word.matches(".*중.*") || word.matches(".*고.*") || word.matches(".*초.*")) {
//                // 초,중,고가 초등학교,중학교,고등학교를 명확하게 의미하는지 확인(전체 단어가 정확히 들어가있는 경우)
//                // 해당 line에 명확하게 적혀있는 글자가 하나도 없을 경우에 약어가 있는지 확인
//                if(word.matches(".*학교.*")) {
//                    for(String indicator : schoolIndicators) {
//                        if(word.matches(".*" + indicator + ".*")) {
//                            if (word.indexOf(indicator) == 0 && index > 0) {    // 띄어쓰기 되어있는 경우 -> 카카오 중학교
//                                word = wordList.get(index - 1) + word;
//                                if(wordList.size() - 1 > 0 && wordList.get(index - 1).equals("여자")) {
//                                    word = wordList.get(index - 2) + word;
//                                }
//                            }
//                            name = word.substring(0, word.indexOf("학교") + 2);   // 학교까지 잘라줌
//                            result += name + " - ";
//                            findFullName = true;
//                        }
//                    }
//                }
//
//
////
////
////
////                if (word.matches(".+초등학교.*") || word.matches(".+중학교.*") || word.matches(".+고등학교.*")) {
////                    // 1.1.1. ~~학교까지만 단어를 잘라서 보관
////                    name = word.substring(0, word.indexOf("학교") + 2);
////                    putSchoolToMap(name);
////                    result = name + " - "; // 테스트용
////                    findFullName = true;
////                }
//                if (!findFullName && !findSimpleName) {
//                    result += makeFullName(word) + " - ";
//                    findSimpleName = true;
//                }
//            } else {
//                if (word.matches(".+대.*")) {
//                    if (word.matches(".+대학교.*")) {
//                        name = word.substring(0, word.indexOf("학교") + 2);
//                        if (!findFullName) {
//                            putSchoolToMap(name);
//                            result = name;
//                            findFullName = true;
////                    } else {                    // 1,3, 명확한 이름의 학교 값이 하나라도 들어왔던 경우
////                        if(isSameSchool(result.replaceAll("(^\\p{Z}+|\\p{Z}+$)", ""), name)) {
//
////                        }
//                        }
//                    }
//                }
//            }
//            index++;
//        }
//
//
//        return result;
////        return result.replaceAll("(^\\p{Z}+|\\p{Z}+$)", "");
////        return result.replaceAll("[^가-힣a-zA-Z]*", "");
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//        // 1. 초등학교, 중학교, 고등학교, 대학교와 같이 이름이 명확하게 적혀있는 학교 명 필터링
////            if (word.matches(".+초등학교.*") || word.matches(".+중학교.*") || word.matches(".+고등학교.*") || word.matches(".+대학교.*")) {
////
////                // 1.1. '~ 학교' 까지 substring
////                String name = word.substring(0, word.lastIndexOf("학교")+2);
////
////                if(findFullName == false) { // 1.2. 아직 명확한 이름의 값이 하나도 안들어온 경우 -> 처음 들어온 값
////                    result += name + " ";   // [테스트용]
//////                    map.put(name, 0);
////                    findFullName = true;
////                } else {                    // 1,3, 명확한 이름의 학교 값이 하나라도 들어왔던 경우
////                    if (!result.contains(name) && !name.contains(result)) {
////                        result += name + " ";
////                    }
////                }
////            } else if (word.matches(".*중") || word.matches(".*고") || word.matches(".*초") || word.matches(".*대")) {
////                //
////                if (findFullName == false) {
////                    result += word + " ";
////                    return result.replaceAll("(^\\p{Z}+|\\p{Z}+$)", "");
////                }
////            }
////        }
////        return result.replaceAll("(^\\p{Z}+|\\p{Z}+$)", "");
//    }
//
//    // 전달된 두 학교 명이 표기상으로는 다르지만, 의미는 같은 경우(예: 홍익대학교부속여자중학교, 홍익대학부속여자중학교) 또는 같은 단어인경우
//    public static boolean isSameSchool(String s1, String s2) {
//        if(s1.equals(s2)) {
//            return true;
//        }
//
//        String[] schoolIndicator = {"초등학교", "중학교", "고등학교", "대학교"};
//        for(int i=0; i<schoolIndicator.length; i++) {
//            if(s1.matches(".+" + schoolIndicator[i])) {
//                s1 = s1.substring(0, s1.indexOf(schoolIndicator[i]) + schoolIndicator[i].length());
//            }
//            if(s2.matches(".+" + schoolIndicator[i])) {
//                s2 = s2.substring(0, s2.indexOf(schoolIndicator[i]) + schoolIndicator[i].length());
//            }
//        }
//
//        if(s1.length() == 1 || s2.length() == 1) {
//            return false;
//        }
//
//        int sameCount = 0;
//        String longStr = "";
//        String shortStr = "";
//
//        if(s1.length() > s2.length()) {
//            longStr = s1;
//            shortStr = s2;
//        } else {
//            longStr = s2;
//            shortStr = s1;
//        }
//
//        HashMap<Integer, Character> map = new HashMap<>();
//        for(int i=0; i<longStr.length(); i++) {
//            map.put(i, longStr.charAt(i));
//        }
//
//        for(int i=0; i<shortStr.length(); i++) {
//            if(map.containsValue(shortStr.charAt(i))) {
//                sameCount++;
//            }
//        }
//
//        if(sameCount == shortStr.length()) {
//            return true;
//        }
//        return false;
//    }
//
//    public static void saveResultToTxt(HashMap<String, Integer> map) throws IOException {
//        BufferedOutputStream bs = null;
//        String schoolNameAndCount = "";
//        try {
//            bs = new BufferedOutputStream(new FileOutputStream("src/result.txt"));
//            for (Map.Entry<String, Integer> entry : map.entrySet()) {
////                System.out.println("[Key]:" + entry.getKey() + " [Value]:" + entry.getValue());
//                //ㅇㅇ중학교\t192\n
//                schoolNameAndCount = entry.getKey() + "\t" + entry.getValue() + "\n";
//                bs.write(schoolNameAndCount.getBytes());
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            bs.close();
//        }
//    }
//
//    public static void putSchoolToMap(String name) {
//        if (map.size() == 0) {
//            map.put(name, 0);
//            return;
//        }
//        for (Map.Entry<String, Integer> entry : map.entrySet()) {
//            String schoolName = entry.getKey();
//            if(isSameSchool(name, schoolName)) {
//                map.put(schoolName, map.get(schoolName)+1);
//                return;
//            }
////                System.out.println("[Key]:" + entry.getKey() + " [Value]:" + entry.getValue())
//
//        }
//        map.put(name, map.getOrDefault(name, 0)+1);
//    }
//
//    public static String makeFullName(String name) {
//        if(name.matches(".+초.*")) {
//            String result = name.substring(0, name.indexOf("초") + 1);
//            return result + "등학교";
//        } else if(name.matches(".+중.*")) {
//            String result = name.substring(0, name.indexOf("중") + 1);
//            if(name.matches(".+여중.*")) {
//                result = name.substring(0, name.indexOf("여중"));
//                result += "여자중";
//            }
//            return result + "학교";
//        } else if(name.matches(".+고.*")) {
//            String result = name.substring(0, name.indexOf("고") + 1);
//            if(name.matches(".+여고.*")) {
//                result = name.substring(0, name.indexOf("여고"));
//                result += "여자고";
//            }
//            return result + "등학교";
//        } else if(name.matches(".+대.*")) {
//            String result = name.substring(0, name.indexOf("대") + 1);
//            if(name.matches(".+여대.*")) {
//                result = name.substring(0, name.indexOf("여대"));
//                result += "여자대";
//            }
//            return name + "학교";
//        }
//        return "";
//    }
//}
