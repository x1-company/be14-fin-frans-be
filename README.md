# FRANS : 대형 프랜차이즈를 위한 주문 관리 시스템
<img alt="image" src="https://github.com/user-attachments/assets/c2282e10-7ef0-4d7b-9a55-235e96047356"/>



## 팀원 

| <img src="https://github.com/user-attachments/assets/55fd8236-71dc-4ca0-9a89-b8eb6fa72b43" width="110"> | <img src="https://github.com/user-attachments/assets/a26c95a2-32ed-4488-b681-4599670dc2c4" width="110"> | <img src="https://github.com/user-attachments/assets/af497517-f615-41b3-9eb0-ac8ee5760895" width="110"> | <img src="https://github.com/user-attachments/assets/66e016e3-691b-4bff-b677-c025986e0cdf" width="110"> | <img src="https://github.com/user-attachments/assets/e5426474-2fa2-49cf-a8d4-cada3143983a" width="110"> | <img src="https://github.com/user-attachments/assets/0d9bee47-ae0e-4381-8b8e-799c2cf17ad1" width="110"> |
| :-----------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------: | :-----------------------------------------------------------------------------------------------------: | :------------------------------------------------------------------------------------------------------: |
| 황수민 | 신민경 | 이예원 | 조윤태 | 이준규 | 박지원 |
| [<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">](https://github.com/emily9949) | [<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">](https://github.com/mmmv41) | [<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">](https://github.com/oni128) | [<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">](https://github.com/cxzaqq) | [<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">](https://github.com/JK-LEE98) | [<img src="https://img.shields.io/badge/Github-Link-181717?logo=Github">](https://github.com/zi-won) |

---

## 프로젝트 기획 배경
[🔗 자세히 보기](https://github.com/x1-company/be14-fin-frans-be/wiki/2.-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EA%B8%B0%ED%9A%8D)
<br><br>
<img src = "https://github.com/user-attachments/assets/e9f34344-8a0e-46ee-81a4-71c30ba5d3a0"/>

### 1. 사용자 불편 사항 조사
ERP 시스템을 실제로 사용하는 가맹점주와 본사 직원을 직접 인터뷰하고, 약 50명을 대상으로 설문조사를 진행한 결과, 기존 주문·재고 관리 시스템에서는 원하는 기능을 찾기 어렵고 메뉴가 복잡하여 사용성이 떨어진다는 의견이 많았습니다. 또한, 로딩 속도가 느리고 실시간으로 정보를 확인하기 어려운 점도 불편함으로 지적되었습니다.

### 2. 주요 Pain Point 및 시장 성장성
프랜차이즈 업계는 지속적으로 성장하고 있으며, 가맹점 수와 매출도 꾸준히 증가하고 있습니다. 이에 따라 현장에서는 더욱 효율적이고 체계적인 관리 시스템에 대한 요구가 높아지고 있습니다. 기존 시스템의 한계가 분명해지면서 실시간 정보 제공과 효율적인 업무 지원의 필요성이 강조되고 있습니다.

### 3. FRANS의 목표
이러한 배경에서 FRANS 프로젝트는 누구나 쉽게 사용할 수 있는 직관적인 UI/UX, 반복 업무를 줄여주는 자동화 및 임시저장 기능, 실시간 알림과 데이터 통합 관리 시스템을 제공하는 것을 목표로 기획되었습니다. 


---

## 프로세스 구조
[🔗 자세히 보기](https://github.com/x1-company/be14-fin-frans-be/wiki/3.-%EC%A3%BC%EC%9A%94-%ED%94%84%EB%A1%9C%EC%84%B8%EC%8A%A4)
<br><br>
<img src = "https://github.com/user-attachments/assets/4dc2f837-29f4-49a0-b068-e54ac6ed9354"/>
### 가맹점
본사에 주문 및 반품 요청 등록. 
### 본사
가맹점의 주문/반품을 승인 및 처리하고, 필요 시 결재 요청을 진행. 재고 현황을 관리하며, 공급처에 발주를 지시.
### 창고
본사 지시에 따라 가맹점에 물품을 배송. 공급처로부터 납품받은 상품을 관리.
### 공급처
본사의 발주 요청에 따라 창고로 상품을 납품.

## 결과물
[🔗 자세히 보기] 

## 기술 스택
<div align="left">

<h3>Backend</h3>
<div dir="auto">
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">

![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)
![Spring JPA](https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
![MyBatis](https://img.shields.io/badge/MyBatis-B5E7A0?style=for-the-badge&logo=MyBatis&logoColor=black)

<h3>Frontend</h3>
<div dir="auto">
<img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
<img src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white"> 
<img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
<img src="https://img.shields.io/badge/vue.js-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white"> 
<img src="https://img.shields.io/badge/vite-%23646CFF.svg?style=for-the-badge&logo=vite&logoColor=white"/>
</div>

<h3>DB</h3>
<img src="https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white">
<img src="https://img.shields.io/badge/Redis-FF4438?style=for-the-badge&logo=redis&logoColor=white">
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/Amazon%20RDS-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white">

</div>

<h3>Infra</h3>
<img src = "https://img.shields.io/badge/GITHUB%20ACTIONS-2088FF?style=for-the-badge&logo=githubactions&logoColor=white"/>
<img src = "https://img.shields.io/badge/AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white"/>
<img src = "https://img.shields.io/badge/ELASTIC%20BEANSTALK-FF9900?style=for-the-badge&logo=awselasticbeanstalk&logoColor=white"/>

<h3>Tools & Communication</h3>
<div dir="auto">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white"/>
<img src="https://img.shields.io/badge/ERDCLOUD-339AF0?style=for-the-badge&logoColor=white">
<img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white">
<img src="https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white"/>
</div>

</div>

## 시스템 아키텍쳐
<img width="8085" alt="frans아키텍쳐" src="https://github.com/user-attachments/assets/ffd4d4ce-8a14-4ac8-b182-395b0e1cff02" />



