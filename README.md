# apache-flink-starter

## 공식 페이지
> https://flink.apache.org/

## 용도
> 실시간 Stream 데이터 처리 & Batch 처리기

## 장점
> 이벤트 시간 시멘틱 기능으로 순서가 바뀐 이벤트가 들어와도 일관성있고 정확한 데이터 처리가 가능  
> 짧은 지연 시간과 높은 처리율을 가진 3세대 분산 오픈소스 스트림 처리기  

## 구성
> DataSource(Kafka, RabbitMQ 같은 MessageQueue) -> ProcessFunctions(분석 프로세스) -> Sink(RDB,Key/Value Store/File 등 분석 결과물 저장)

## 용어 정리
> 내고장성: 시스템의 일부가 고장이 나도 전체에는 영향을 주지 않고, 항상 시스템의 정상 작동을 유지하는 능력.

## 주의
### SpringBoot
> Apache Flink 는 SpringBoot 와 사용할 수 없다.   
> Apache Flink 에서 Function 을 implement 하여 사용하는 클래스에 Spring @Component 애노테이션 사용하여 Spring bean 주입을 시도하였으나
> Flink 실행 시에 bean 주입한 객체에 NullPointerException 이 발생하였다.(bean 주입 불가능)   
> Flink 프로그램을 통해서 Flink Project 를 실행하여야하는데 Spring ApplicationRunner 를 implement 한 Runner 클래스의 실행 불가능.
> SpringBoot main 메서드에서 실행해도 마찬가지.

## 참조사이트
> https://www.samsungsds.com/kr/insights/flink.html

## 설치
### Flink Cluster
> macOS 사용시 `brew install apache-flink` 로 설치  
> flink bin 으로 이동: `cd /usr/local/Cellar/apache-flink/1.14.2/libexec/bin`  
> start cluster bash 실행: `./start-cluster.sh`

## 프로젝트
### 자바(Java) 프로젝트 생성
> 터미널에서 maven 을 사용한 프로젝트 생성(IntellJ 에서 안됨. Spring 안됨.)
> ```
> mvn archetype:generate \
> -DarchetypeGroupId=org.apache.flink \
> -DarchetypeArtifactId=flink-quickstart-java \
> -DarchetypeVersion=1.14.2 \
> -DgroupId=practice.apache.flink \
> -DartifactId=apache-flink-starter \
> -Dversion=0.1-SNAPSHOT \
> -Dpackage=practice.apache.flink \
> -DinteractiveMode=false
> ```

### pom.xml
> IntelliJ 실행 환경 추가   
> pom.xml 파일 가장 아래에 다음 추가
> ```xml
> <!-- This profile helps to make things run out of the box in IntelliJ -->
>	<!-- Its adds Flink's core classes to the runtime class path. -->
>	<!-- Otherwise they are missing in IntelliJ, because the dependency is 'provided' -->
>	<profiles>
>		<profile>
>			<id>add-dependencies-for-IDEA</id>
>
>			<activation>
>				<property>
>					<name>idea.version</name>
>				</property>
>			</activation>
>
>			<dependencies>
>				<dependency>
>					<groupId>org.apache.flink</groupId>
>					<artifactId>flink-java</artifactId>
>					<version>${flink.version}</version>
>					<scope>compile</scope>
>				</dependency>
>				<dependency>
>					<groupId>org.apache.flink</groupId>
>					<artifactId>flink-streaming-java_${scala.binary.version}</artifactId>
>					<version>${flink.version}</version>
>					<scope>compile</scope>
>				</dependency>
>				<dependency>
>					<groupId>org.apache.flink</groupId>
>					<artifactId>flink-clients_${scala.binary.version}</artifactId>
>					<version>${flink.version}</version>
>					<scope>compile</scope>
>				</dependency>
>			</dependencies>
>		</profile>
>	</profiles>
> ```
> 
> lombok 추가   
> IntelliJ 실행 환경 <dependencies> 말고 윗쪽 <dependencies> 태그 안에 추가
> ```xml
> <dependency>
> 	 <groupId>org.projectlombok</groupId>
> 	 <artifactId>lombok</artifactId>
> 	 <version>1.18.22</version>
> 	 <scope>compile</scope>
> </dependency>
> ```

## 실행
### IDE
> IntelliJ 에서는 메인함수 실행하면 된다.

### Flink cluster
> 0.flink 설치     
> macOS: `brew install apache-flink`     
> 
> centOS   
> ```bash
> wget https://dlcdn.apache.org/flink/flink-1.14.2/flink-1.14.2-bin-scala_2.12.tgz
> tar xfz ./flink-1.14.2-bin-scala_2.12.tgz
> ```
> 
> 1.클러스터 설정  
> 주의) 다중 서버(Multi Node) 인 경우만 설정한다. 로컬 실행인 경우에는 넘어가자.  
> 이 설정은 모든 서버(Node) 에 동일하게 적용한다.    
> 아래 설정은 Master 서버 1대(192.168.1.3) 와 Worker 서버 2대(192.168.1.4, 192.168.1.5) 를 가정하고 설정하였다.  
> ```bash
> cd ./flink-1.14.2-bin-scala_2.12/conf
> 
> # 현재 서버 IP 주소로 변경
> vi flink-conf.yaml
> jobmanager.rpc.address: 192.168.1.3
> :wq
> 
> # master 서버 IP localhost -> IP 로 교체
> vi masters
> 192.168.1.3:8081
> :wq
> 
> # worker 서버 IP 주소 추가
> vi workers
> 192.168.1.4
> 192.168.1.5
> :wq
> ```
> 
> 2.클러스터 실행  
> 클러스터 실행은 Master 서버(Node)에서만 하면 된다.  
> macOS  
> ```bash
> bash /usr/local/Cellar/apache-flink/1.14.2/libexec/bin/start-cluster.sh
> ```
> centOS  
> ```bash
> bash ./flink-1.14.2-bin-scala_2.12/bin/start-cluster.sh
> ```
> 
> 3.Job 제출    
> Flink 1.14.2 버전에서 현재 Java 11 버전 지원안함으로 Java 8 버전을 이용해서 mvn package 한다.  
> 3-1.main 클래스가 1개인 경우: `flink run [jar 파일명].jar`  
> 예시) `flink run apache-flink-starter-0.1-SNAPSHOT.jar`  
> 
> 3-2.main 클래스가 여러개여서 특정 main 클래스를 실행할 경우: `flink run -c [패키지명].[main 클래스 명] [jar 파일명].jar`    
> 예시) `flink run -c practice.apache.flink.StreamingJob apache-flink-starter-0.1-SNAPSHOT.jar`  
>
> 3-3.Web UI 를 통한 제출  
> `localhost:8081` 접속     
> 좌측 메뉴 맨 아래 `Submit New Job` -> `+ Add New` 를 통해서 maven build 한 jar 파일 선택해서 submit    

