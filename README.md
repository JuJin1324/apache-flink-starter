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

## 운영
### Job 제출
> Flink 1.14.2 버전에서 현재 Java 11 버전 지원안함으로 Java 8 버전을 이용해서 mvn package 한다.  
> 1.Command Line 을 통한 제출  
> `./bin/flink run <jar 파일명>.jar`  
> 예시) `./bin/flink run apache-flink-starter-0.1-SNAPSHOT.jar`
>
> `-c`: Main class 지정    
> 예시) `./bin/flink run -c practice.apache.flink.WordCountJob apache-flink-starter-0.1-SNAPSHOT.jar`
>
> `-d`: Daemon 실행      
> 
> `-p <갯수>`: 연산자 병렬 실행 갯수   
> 주의) 소스 코드에 StreamExecutionEnvironment 를 통해서 직접 병렬 실행 갯수를 설정했다면 해당 설정은 적용이 안된다.
> 
> `-m <서버 IP>:<Port>`: Job 을 제출할 Master 서버 지정  
>
> 2.Web UI 를 통한 제출  
> `localhost:8081` 접속     
> 좌측 메뉴 맨 아래 `Submit New Job` -> `+ Add New` 를 통해서 maven build 한 jar 파일 선택해서 submit

### 실행 중인 애플리케이션 목록 보기
> `./bin/flink list -r`

### Flink Cluster 개요 확인
> Master 서버(Node) 가 localhost 라고 가정   
> `http GET http://localhost:8081/v1/overview`

### Log
> 실행 로그 위치: log4j2.properties 에 설정된 위치

## Flink Cluster 설정
### Master Node
> Master 프로세스는 주로 애플리케이션 자원 관리 및 조율을 담당함으로 적당한 수준의 메모리만 필요하다.   
> `flink-conf.yaml` 에서 `jobmanager.memory.process.size` 는 기본 1600MB 로 설정되어 있으며 많은 애플리케이션을 관리할 경우 메모리를 늘려 사용한다.  

### 병렬 설정
> 애플리케이션을 로컬에서 실행 시 기본 병렬 값은 로컬 머신의 CPU 갯수만큼이다.
> (코어가 아닌 스레드 갯수, 예를 들어 CPU 가 4코어 8쓰레드 이면 8개의 연산자 태스크가 생성된다.)   
> 클러스터 환경에서 애플리케이션을 제출할 시 병렬 값을 지정해서 제출하지 않으면 기본 1개의 병렬 값을 가진다.
> 
> 보통 연산자의 병렬 값은 실행 환경 기본 값의 상댓 값으로 정의하는 것이 좋다.
> ```java
> int defaultP = env.getParallelism();
> dataStream
>   .map(r -> new SensorReading(r.id, r.timestamp, (r.temperature - 32) * (5.0 / 9.0)))
>   .keyBy(r -> r.id)
>   .timeWindow(Time.seconds(1))
>   .apply(new TemperatureAverager())
>   .setParallelism(defaultP * 2);     // 기본 값의 상댓 값으로 정의
>```

### RichFunction
> Flink 는 모든 함수 객체를 자바 직렬화를 이용해 직렬화한 후 작업 태스크로 전송한다. 함수에 포함된 모든 것은 Serializable 이어야 한다.  
> 함수가 직렬화 불가능한 객체 인스턴스를 포홤할 때는 RichFunction 으로 함수를 구현하고 직렬화 불가능한 필드를 open() 메서드에서 초기화한다.
> 직렬화가 불가능한 객체 인스터스에는 앞에 transient 키워드를 붙여 직렬화 제외 객체임을 표시한다.   
> 
> open() 메서드의 Configuration 매개 변수는 DataSet API 에서만 사용됨으로 DataStream API 에서는 무시한다.

### Watermark
> 워터마크는 워터마크보다 작거나 같은 타임스탬프를 가진 이벤트가 더 없을 것이라고 연산자에게 알려준다.   
>
> 가능하면 타임스탬프 할당과 워터마크 생성을 Source 근처 또는 SourceFunction 안에서 하는 것이 가장 좋다.(다른 곳에서 하면 이벤트 순서가 변경될 수 있다.)
>
> flink 1.12 버전 이후부터는 이벤트 타임 특성 설정을 위해서 `env.setStreamTimeCharacteristic(TimeChracteristic.EventTime);` 를 선언할 필요가 없다.
> default 로 EventTime 특성으로 설정되어있다.
> 

## 용어 정리
> upper bound: 연착 상한 시간
> 

## 참조사이트
> [이벤트 시간 처리(Event Time Processing)와 워터마크(Watermark)](https://seamless.tistory.com/99)
> [Flink 시작하기 #1 소개 (Basic Concept)](https://gyrfalcon.tistory.com/entry/Flink-1-%EC%86%8C%EA%B0%9C-Basic-Concept)
