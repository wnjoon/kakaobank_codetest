import java.util.ArrayList;
import java.util.HashMap;

public class SchoolFinder {

    // row를 단어별로 분리(split)해서 해당 단어에 학교에 관련된 내용이 있는지 찾는 부분
    public static void find(String[] line, HashMap<String, Integer> map) {

        // Splitter -> [TODO] 각각에 맞게 필터링되는 단어들 확인 필요!!
        // String[] words = (String.join("", line)).split(" |,|\\?|\\(|\\)|[!]|[❤]|[♡]|[★]|\\n|\\/|-|\\<|\\>|❣️|~|#|\\.|\\-|❗");
        String[] words = (String.join("", line)).split("\\s|\\n|,|!|\\?|\\.|\\*|/|★|ㅠ|♡새|>|<");
        ArrayList<String> wordList = new ArrayList<>();
        boolean foundLessThanOneWord = false;

        int index = 0;      // 한 row 에서 split 된 모든 단어들을 저장 (향후 앞뒤로 비교해서 정제된 단어를 생성하기 위해)
        for (String word : words) {
            word = word.replaceAll("[^가-힣A-Z]*", ""); // 한글, 영어를 제외한 모든 단어 삭제
            // Dummy check
            wordList.add(word); // 우선 단어 저장
            word = isDummyWord(word);
            if (word.equals("")) {
                index++;
                continue;
            }

            /* Case 1
             * --초-- , --중-- , --고--
             */
            if (word.matches(".*초.*") || word.matches(".*중.*") || word.matches(".*고.*")) {
                /*
                 * --초등학교-- , --중학교-- , --고등학교--
                 */
                if (word.matches(".+학교.*")) {
                    String[] schoolIndicators = {"초등학교", "중학교", "고등학교"};
                    for (String indicator : schoolIndicators) {
                        if (word.matches(".*" + indicator + ".*")) { // 어딘가에 포함되어 있으면
                            String pureWord = getPureSchoolName(word, indicator);

                            if(!pureWord.equals("")) {
                                pureWord = checkExtraSchoolInfo(index, wordList, pureWord, indicator);  // indicator 앞에 붙어야 할 단어가 있는지 확인
                                if(!pureWord.equals(indicator)) {
                                    // System.out.println("+[" + index + "]" + pureWord + " ");
                                    putSchoolToMap(map, pureWord);
                                    foundLessThanOneWord = true;
                                } else {
                                    continue;
                                }

                                // word 에 남은 pureWord 있는지 확인? (개봉중학교)
                                int pureWordLen = pureWord.length();
                                if(word.length() >= pureWordLen) {
                                    word = word.substring(word.indexOf(pureWord) + pureWordLen);
                                    while(word.length() >= pureWordLen) {
                                        if(word.contains(pureWord)) {
                                            //System.out.println("+[" + index + "]" + pureWord + " ");
                                            putSchoolToMap(map, pureWord);
                                            word = word.substring(word.indexOf(pureWord) + pureWordLen);
                                            foundLessThanOneWord = true;
                                        } else {
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                /*
                 * --초--, --중--, --고--
                 */
                } else {
                    // --중--, --고--
//                    if (word.matches(".+중.*") || word.matches(".+고.*")) {
                        // 중학교 또는 고등학교를 지칭하는 조사와 함께하는 경우
                        if (word.matches(".+초.+") || word.matches(".+중.+") || word.matches(".+고.+")) {
                            if (word.contains("초요")) {
                                word = word.substring(0, word.indexOf("초") + 1);
                            } else if (word.contains("중이") || word.contains("중오") || word.contains("중으로")) {
                                word = word.substring(0, word.indexOf("중") + 1);
                            } else if (word.contains("고에") || word.contains("고")) {
                                word = word.substring(0, word.indexOf("고") + 1);
                            } else {
                                index++;
                                continue;
                            }
                        }
                        word = getSchoolNameFromSimple(word);
                        if(!word.equals("")) {
                            //System.out.println(">[" + index + "]" + word + " ");
                            putSchoolToMap(map, word);
                            foundLessThanOneWord = true;
                        }
//                    }
                }
            /*
             * --대--
             */
            } else if (word.matches(".+대학교.*")) {
                word = getPureSchoolName(word, "대학교");
                //System.out.println(word + " ");
                putSchoolToMap(map, word);
                foundLessThanOneWord = true;
            } else {
                index++;
                continue;
            }
            index++;
        }

        // 하나도 찾은 단어가 없을 경우
        // 다시 한번 전체 row 를 돌면서 학교 이름에 해당하는 단어가 있는지 확인
        if(!foundLessThanOneWord) {
            StringBuilder sb = new StringBuilder();
            for(String element : wordList) {
                sb.append(element);
            }
            String word = sb.toString();

            // --- 학교 라는 이름이 있는지 확인
            if (word.matches(".+학교.*")) {
                String[] schoolIndicators = {"초등학교", "중학교", "고등학교"};
                for (String indicator : schoolIndicators) {
                    if (word.matches(".*" + indicator + ".*")) { // 어딘가에 포함되어 있으면
                        String pureWord = getPureSchoolName(word, indicator);

                        // 명확한 기준을 세우기 어렵지만, 대략 걸러낸 학교 이름이 20단어 이상이면 하나도 걸러지지 않은 상태로 인식

                        if(!pureWord.equals("")) {
                            if (pureWord.length() > 20) {
                                continue;
                            }
                            int pureWordLen = pureWord.length();
                            if (word.length() >= pureWordLen) {
                                //word = word.substring(word.indexOf(pureWord) + pureWordLen);
                                while (word.length() >= pureWordLen) {
                                    if (word.contains(pureWord)) {
                                        //System.out.println("+[ repeat ]" + pureWord + " ");
                                        putSchoolToMap(map, pureWord);
                                        word = word.substring(word.indexOf(pureWord) + pureWordLen);
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /*
     * 약어(--초, --중, --고, --대)로 표현되어있는 학교 명을 --초등학교, --중학교, --고등학교, --대학교와 같은 이름으로 변경
     * 출력되는 값의 형태에 통일성을 주기 위해서 작성함
     */
    public static String getSchoolNameFromSimple(String name) {

        if(name.matches(".+초.*") && name.indexOf("초") != 0) {
            return name.substring(0, name.indexOf("초")) + "초등학교";
        } else if(name.matches(".+중.*") && name.indexOf("중") != 0 && name.indexOf("여중") != 0) {
            if(name.matches(".+여중.*")) {
                return name.substring(0, name.indexOf("여중")) + "여자중학교";
            }
            return name.substring(0, name.indexOf("중")) + "중학교";
        } else if(name.matches(".+고.*") && name.indexOf("고") != 0 && name.indexOf("여고") != 0) {
            if(name.matches(".+여고.*")) {
                return name.substring(0, name.indexOf("여고")) + "여자고등학교";
            } else if (name.matches(".+예고.*")) {
                return name.substring(0, name.indexOf("예고")) + "예술고등학교";
            } else if (name.matches(".+체고.*")) {
                return name.substring(0, name.indexOf("체고")) + "체육고등학교";
            }
            return name.substring(0, name.indexOf("고")) + "고등학교";
        } else if(name.matches(".+대.*") && name.indexOf("대") != 0) {
            if(name.matches(".+여대.*")) {
                return name.substring(0, name.indexOf("여대")) + "여자대학교";
            }
            return name.substring(0, name.indexOf("대")) + "대학교";
        }
        return "";
    }

    // 해시맵에 학교 입력(put)
    public static void putSchoolToMap(HashMap<String, Integer> map, String school) {
        map.put(school, map.getOrDefault(school, 0) + 1);
    }

    // 학교와 전혀 관련없는 단어들이 들어있는지 확인
    public static String isDummyWord(String word) {
        /*
         * 명확한 규칙을 찾는것이 쉽지 않았음
         * 한번씩 프로그램을 돌려가면서 학교의 이름을 헷갈리게(?)하는 단어들을 별도로 추출하여 정리함
         */
        String[] ignoreIndicator = {"굶", "똥", "받", "씻", "쏘", "불", "못", "움", "풀", "놓", "참",
                "먹", "없", "쌓", "좋", "끝", "된", "때", "힘", "뽑", "났", "쏘", "폭", "몇", "있", "듣",
                "프", "깔", "렇", "능", "쁘", "뿌", "엽", "쓰", "급", "르", "밝", "되", "짜", "골", "콩",
                "짬", "킨", "류", "트", "질", "렵", "꼭", "웃", "걸", "특", "달", "챙", "절", "픕", "따",
                "답", "타", "르", "첨", "댓", "억", "놀", "음", "면", "최", "크", "잊", "프", "뭐", "콩",
                "험", "앞", "복", "작", "딩", "롭", "얻", "덜", "같", "않", "키", "누", "있", "싶", "벌",
                "겁", "말", "간", "알", "닌", "찌", "좁", "꾸", "쳐", "챙", "드", "믿", "싸", "으", "밀",
                "루", "그", "얹", "넣", "더", "겠", "째", "럽", "망",
                "혜민", "혜란", "예림", "혜란", "글고", "하고", "입고", "라고", "오고", "우고", "이고", "치고",
                "화내", "시고", "지고", "보고", "기고", "최고", "작고", "주고", "나고", "들고", "박고", "고생",
                "다고", "가고", "배고", "신고", "수고", "니고", "리고", "활기", "소중", "나중", "문제", "잇고",
                "안고", "적고", "재학", "라서", "준대", "저희", "예비", "명문", "에서", "준비", "공부", "울초",
                "연합", "역사",
                "맛있고", "학생중", "진현중", "김민중", "제대로", "중학고", "자사고", "보내고", "마이스",
                "경기도고", "중고등학교"};

        // 헷갈리게 하는 단어와 별개로 명확하게 이름이 명시되어 있는 학교는 별도로 분리해놓음
        String[] exceptionSchoolNames = {"유신고", "정보고", "함지고", "성수고", "문수고", "동두천",
                                         "인천체고", "창덕여고", "율현"};

        String[] replaceBlankWords = {"우리"};
        for(String replaceBlankWord : replaceBlankWords) {
            if(word.contains(replaceBlankWord)) {
                word = word.replace(replaceBlankWord , "");
            }
        }

        for(String exceptionSchoolName: exceptionSchoolNames) {
            if(word.matches(".*" + exceptionSchoolName + ".*")) {
                return word.substring(word.indexOf(exceptionSchoolName));
            }
        }

        for(String ignoreWord : ignoreIndicator) {
            if(word.contains(ignoreWord)) {
                return "";
            }
        }
        return word;
    }

    // 앞에 특별한 단어가 추가되어야 하는지(연결해줘야 하는지) 확인
    public static String checkExtraSchoolInfo(int index, ArrayList<String> wordList, String word, String indicator) {
        String[] extraIndicators = {"여자", "국제통상"};
        if(index > 0) {
            for(String extraIndicator : extraIndicators) {
                if (word.indexOf(extraIndicator + indicator) == 0) {
                    // 여자--- 또는 국제통상--- 까지만 명시 -> 앞에 단어 추가 필요
                    if (wordList.size() > 0) {
                        word = wordList.get(index - 1) + word;
                    }
                } else if (word.indexOf(indicator) == 0) {
                    // --- 까지만 명시 -> 확인 필요
                    if (wordList.size() > 1) {
                        if (wordList.get(index - 1).equals(extraIndicator)) {
                            word = wordList.get(index - 2) + wordList.get(index - 1) + word;
                        } else {
                            if(wordList.get(index-1).equals("병점") || wordList.get(index-1).equals("정화")) {
                                word = wordList.get(index - 1) + word;
                            }
                        }
                    }
                }
            }
        }
        return word;

    }

    // ---학교-- -> ---학교
    public static String getPureSchoolName(String word, String indicator) {
        return word.substring(0, word.indexOf(indicator) + indicator.length());   // --Indicator 까지 단어를 자름
    }
}
