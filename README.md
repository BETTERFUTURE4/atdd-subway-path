<p align="center">
    <img width="200px;" src="https://raw.githubusercontent.com/woowacourse/atdd-subway-admin-frontend/master/images/main_logo.png"/>
</p>
<p align="center">
  <a href="https://techcourse.woowahan.com/c/Dr6fhku7" alt="woowacuorse subway">
    <img alt="Website" src="https://img.shields.io/website?url=https%3A%2F%2Fedu.nextstep.camp%2Fc%2FR89PYi5H">
  </a>
  <img alt="GitHub" src="https://img.shields.io/github/license/woowacourse/atdd-subway-subwayGraph">
</p>

<br>

# 지하철 노선도 미션

스프링 과정 실습을 위한 지하철 노선도 애플리케이션

<br>

## 🚀 Getting Started

### Usage

#### application 구동

```
./gradlew bootRun
```

<br>

## ✏️ Code Review Process

[텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

<br>

## 🐞 Bug Report

버그를 발견한다면, [Issues](https://github.com/woowacourse/atdd-subway-subwayGraph/issues) 에 등록해주세요 :)

<br>

## 📝 License

This project is [MIT](https://github.com/woowacourse/atdd-subway-subwayGraph/blob/master/LICENSE) licensed.

# 기능 요구사항

## 1단계 요구사항 도출

### 지하철역

- [x] 지하철 역을 등록한다.
    - [x] 같은 역의 이름으로 중복될 수 없다.
    - [x] 역 이름은 10글자를 넘길 수 없다.
    - [x] 역 이름은 2글자 이상이어야 한다.
    - [x] 역 이름은 한글과 숫자의 조합이어야 한다.
- [x] 지하철 역을 조회한다.
- [x] 지하철 역을 삭제한다.
    - [x] 삭제할 역이 있어야 한다.

### 지하철 노선

- [x] 지하철 노선을 등록한다.
    - [x] 같은 노선의 이름으로 중복될 수 없다.
    - [x] 색깔은 중복 될 수 없다.
    - [x] 노선 이름은 10글자를 넘길 수 없다.
    - [x] 노선 이름은 3글자 이상이어야 한다.
    - [x] 노선 이름은 한글과 숫자의 조합이어야 한다.
    - [x] 이름과 색깔이 있어야 한다.
- [x] 지하철 노선 목록을 조회한다.
- [x] 지하철 노선 조회
    - [x] 조회할 노선이 있어야 한다.
- [x] 지하철 노선 수정
    - [x] 같은 노선의 이름으로 중복될 수 없다.
    - [x] 색깔은 중복 될 수 없다.
    - [x] 노선 이름은 10글자를 넘길 수 없다.
    - [x] 노선 이름은 3글자 이상이어야 한다.
    - [x] 노선 이름은 한글이어야 한다.
    - [x] 이름과 색깔이 있어야 한다.
- [x] 지하철 노선 삭제

## 2단계 요구사항 도출

- [x] H2 설정하기
- [x] DAO 에 JDBC Template 사용하여 DB 에 저장하기
- [x] 스프링 빈으로 변경
- [x] 기존 테스트코드를 DAO test 로 변경

## 3단계 요구사항 도출

### 지하철 노선

- [x] 노선을 등록한다.
    - [x] 상행 지점, 하행 지점, 거리가 있어야 한다.
- [x] 노선을 조회한다.
    - [x] 노선이 가진 구간들을 정렬해서 반환한다.
- [x] 노선 목록을 조회한다.
    - [x] 노선들이 가진 구간들을 정렬해서 반환한다.

### 지하철 구간

- [x] 구간을 등록한다.
    - [x] 존재하는 노선이어야 한다.
    - [x] 추가하려는 구간이 상행역이나 하행역이 있어야 한다.
    - [x] 상행역과 하행역이 둘다 존재할 수 없다.
    - [x] 추가하려는 거리가 기존 구간의 거리보다 짧아야 한다.
    - [x] 거리는 음수일 수 없다.
- [x] 구간을 삭제한다.
    - [x] 삭제하려는 구간이 종점일 경우 이전역이 종점이 된다.
    - [x] 중간역이 삭제되는 경우 재배치된다.
    - [x] 구간이 2개 이상이어야 한다.

---

## 1단계 요구사항 도출

### 지하철 경로 조회 API

- [x] 경로를 조회한다.
    - [x] jgrapht 라이브러리를 활용한다.
        - [x] 점(vertex)과 간선(edge), 그리고 가중치 개념을 이용
            - [x] 정점: 지하철역(Station)
            - [x] 간선: 지하철역 연결정보(Section)
            - [x] 가중치: 거리
        - [x] 최단 거리 기준 조회 시 가중치를 거리로 설정
    - [x] 최단 경로 및 거리를 반환한다.
    - [x] 경로가 존재하는지 검증한다.

- [x] 요금을 계산한다.
    - [x] 기본운임(10㎞ 이내): 기본운임 1,250원
    - [x] 이용 거리 초과 시 추가운임 부과
        - [x] 10km~50km: 5km 까지 마다 100원 추가
        - [x] 50km 초과: 8km 까지 마다 100원 추가
        - [x] 지하철 운임은 거리비례제로 책정된다.

- 9km = 1250원
- 12km = 10km + 2km = 1350원
- 16km = 10km + 6km = 1450원
- 58km = 10km + 40km + 8km = 2150원

## TODO

### 1단계 피드백

- [x] 한번만 쓰이는 private 메소드의 경우 쓰이는 곳 밑에 두기
- [x] 엔티티의 id는 새로 생성된 경우 null로 나타내기 위해 Long 타입을 사용 -> 다른 필드도 Long으로 나타낼 필요가 있을까요?
    - 이미 값이 엔티티에 들어올 때 Long으로 들어오기 때문에 그대로 뒀다. 오토박싱/언박싱 과정에서 시간이 소요될 것 같았다.
    - 혹시 그럼에도 박싱된 클래스를 쓰지 않는 편이 낫다면 그 이유는 무엇인가요?
- [x] 무엇을 validate하는지 혹은 어떤 조건을 validate하는지 메서드명에 나타내보면 어떨까요?
- [x] Update는 동사인데, updatedSections를 찾는 메서드일까요?
- [x] 메서드를 호출하는 입장에서는 next 파라미터가 무엇을 의미하는지 알 수 있을까요?
- [ ] find 뿐만 아니라 인자로 받은 result에 add도 하고 있네요. 이 메서드에 더 적절한 이름이 있을까요?
- [ ] 매직 넘버는 상수로 분리하면 어떨까요?
- [ ] DB 엔티티와 도메인 객체 사이의 변환을 책임지는 계층인가보네요 💯 이 영역을 별도로 두었을 때 어떤 장점이 있었나요?
- [ ] id가 LineUpdateRequest 내에 포함되면 어떨까요?
- [ ] 2단계 요구사항이 반영된 걸까요?
- [ ] 불필요한 변경사항일까요?
- [ ] 예기치 못한 곳에서 예외가 발생하면 로그만 보고 어느 부분을 수정해야 하는지 파악할 수 있어야 할 텐데요. 예외의 메시만으로 충분할까요?
- [ ] 💯 예외 중에는 사용자에게 노출되면 곤란한 메시지를 담고 있는 경우도 있습니다. 이런 경우에는 사용자에게는 적당한 메시지를 보여주고 상세 정보를 로그로 남기는 게 중요합니다 :)
