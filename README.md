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

