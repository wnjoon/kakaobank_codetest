# kakaobank_codetest
2021년 상반기 카카오뱅크에서 진행한 코딩테스트 제출 파일

## 문제 내용
- comments.csv 파일에서 학교 이름을 가장 많이 뽑아내는 프로그램 개발
- xxx학교, xxx초등학교, xxx초, xxx중, 심지어 대학교에 xxxx부설대학고등학교 등등 엄청나게 많음
- 배달의 민족에서 진행한 이벤트 댓글들이라 그런가, 규칙이 없고 학생들이 흔히 사용하는 줄임말+은어 등등이 엄청나게 섞여있음

## 해결 방안
- xxx 초등학교, 중학교, 고등학교, 대학교는 생각보다 필터링하기가 쉬웠음
- 문제는 초, 중, 고, 대로 끝나느 단어가 과연 학교인지 아닌지 여부를 판단하기가 어려웠음
- 결국에는.. 일일이 필터링 돌려가면서 예외문자에 해당 내용을 추가해버리는.. 아주 최악의 코딩을 진행함
- 겉으로는 일의 바쁨과 시간 없음을 이유로 들었지만, 내면에는 어떻게 필터링 해야할지 감이 오지 않았음

## 결론
- 최대한 학교 이름을 추려냄
- 각각을 어떻게 구현했는지(왜 이렇게 했는지) 적고싶었는데, 제출한지 오래되어 그런가 기억이 나지 않음
- 다양한 규칙을 필터링할 수 있는 좋은 방법이 있다면 추후 적용해보고싶다..

## 환경
- openjdk-16
- Maven
