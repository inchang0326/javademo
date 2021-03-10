package com.example.javademo.part.com.controller;

import com.example.javademo.exception.ServiceException;
import com.example.javademo.fwk.base.BaseController;
import com.example.javademo.part.com.service.Sender;
import com.example.javademo.part.com.service.MySender;
import com.example.javademo.part.com.service.OneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/*
    ■ Servlet의 배경과 Spring MVC와의 차이
      초기엔 [사용자- WEB 서버] 사이 정적 데이터만 취급했다. 하지만 기술이 점점발전하면서 사용자를 인식하고,
      상황에 따른 동적 데이터 취급을 원했다. 그래서 동적 데이터를 처리하는 CGI(Common Gateway Interface)
      규약이 탄생하게 되었다. 하지만 [WEB 서버- CGI] 사이 사용자별 Process가 많이 만들어짐에 따라 비효율이
      야기되었는데, 이를 해결하기 위해 Multi-thread를 통해 사용자 요청을 처리하는 WEB 서버환경(Servlet)이
      구성되었고, Singleton Pattern이 적용 되었다. 또한 데이터를 처리 및 가공하여 App 형태로 응답값을 제공
      하는 WAS(Web Application Server)가 탄생했다.
      사용자 요청 시 Servlet 상속한 HttpServlet 인터페이스가 Servlet 인스턴스를 생성(Init)-수행(Service)-종료
      (Destroy)한다. Spring MVC는 Servlet을 Front Controller인 Dispatcher Servlet로 사용한다.
      Spring MVC가 없을 때는 사용자 요청마다(URI) Servlet이 각각 만들어졌지만, Dispatcher Servlet을 통해
      사용자의 많은 요청에도 단 1개의 Servlet만 만들어져 자원을 절약한다(Front Controller).
      또한 Controller, Model, View를 분리하면서 응집도를 높이고 결합도를 낮추는 모듈화가 가능해졌다.
      Spring은 Serlvet의 다양한 환경설정을 Annotation 기반으로 간편하게 대체 할 수 있다는 특징도 갖는다.

    ■ Spring의 Thread-safe
      사용자 요청은 Spring Framework에서 Thread로 처리되며,
      사용자 요청을 처리하는 Bean 객체는 모두 Singleton Parttern을 통해 생성된다.
      즉, 사용자 각각의 요청이 하나의 Bean을 공유하는 Multi-Thread 환경이 자연스럽게 구성되는 것이다.
      그럼에도 Thread-safe한 이유는 Thread는 Stack 영역데이터(지역변수, 매개변수, 리턴변수)를
      공유하지 않는다는 특성 때문이다. 사용자 요청처리를 처음맡는 Bean(Controller)부터 비즈니스 로직을
      수행하는 Bean(Service)까지 일반적으로 데이터를 매개변수를 통해 전달한다.
      그리고 이 매개변수는 Java 메모리 Stack 영역에 저장되어 Thread-safe하게 동작하게 되는 것이다.
      단, 만약 Bean 자체가 상태값(멤버변수)을 갖게 된다면, Thread-safe하지 않을 수 있으니 주의해야 한다.

    ■ Spring과 SpringBoot의 차이
      Spring Framework는 개발자에게 비즈니스 로직만 처리할 수 있도록 도와줌으로 엔터프라이즈 애플리케이션
      을 보다 쉽게 만들 수 있다.

      * Spring의 가장 큰 특징은 다음 2가지다.
      (1) DI 및 IoC, Filter/Interceptor/AOP
      : 개발자가 비즈니스 로직 구현에 집중할 수 있도록 하고, 중복 코드를 제거한다.
      (2) Annotation : 편리한 개발 설정이 가능하다
      * SpringBoot는 Spring을 좀 더 쉽게 사용할 수 있게 해주는 것이다.
      (1) 보다 쉬운 Annotation 설정
      Ex) @SpringBootApplication 하나에 다양한 Annotation을 포함      ㄴ@SpringBootConfiguration : Bean 생성      ㄴ@ComponentScan : Bean 자동 등록      ㄴ@EnableAutoConfiguration : Bean 자동 등록
      (2) 간편한 dependency 설정  Ex) Spring-boot-starter-web 추가 시 웹 개발에 필요한 다양한 환경 설정 자동 처리
      (3) 프로젝트를 JAR 파일로 만들 수 있다.
      JAR, WAR 모두 Java 압축 아카이브 파일이며, App을 쉽게 배포하고 동작 시킬 수 있도록 있도록 관련 파일
      을(리소스, 속성파일등) 패키징해준다

      ▶ JAR(Java Archive)
      .jar는 Java App을 동작할 수 있도록 자바 프로젝트를 압축한 파일로, Class, 속성파일, 라이브러리 파일 등
      이 포함되어 있다. Maven을 통해 내려받는 라이브러리를 본다면 이처럼 Class 파일들이 묶인 jar파일로 구성
      되어 있는 것을 확인할 수 있다. JAR 파일은 원하는구조로 구성이 가능하며 JDK(Java Development Kit)에
      포함하고 있는 JRE(Java Runtime Environment)만 가지고도 간단히 실행가능하다.
      ▶ WAR(Web Application Archive)
      .war는 JSP&Servlet 컨테이너에 배치할 수 있는 웹 애플리케이션 압축파일로,
      JSP, Servlet, Jar, Class, XML, HTML, Javascript 등 Servlet Context 관련 파일로 패키징되어있다.
      WAR는 웹 응용 프로그램을 위한 포맷이기 때문에 웹 관련 자원만 포함하고 있으며, 이를 사용하면
      웹 애플리케이션을 쉽게 배포하고 테스트할 수 있다. 배포 시 프로젝트를 WAR 포맷으로 묶어서/webapps
      지정 경로에 넣고 Tomcat 등 의 웹컨테이너(Web Contaioner)를 이용하여 deploy하면 된다.
      원하는 구성을할 수 있는 JAR 포맷과 달리 WAR은 WEB-INF 및 META-INF 디렉토리로 사전 정의된 구조를
      사용하며 WAR파일을 실행하려면 Tomcat, Weblogic, Websphere 등의웹서버(WEB) 또는웹컨테이너(WAS)가
      필요하다. WAR 파일도 자바의 JAR 옵션(java-jar)을 이용해 생성하는 JAR 파일의 일종으로 웹 애플리케이션
      전체를 패키징하기 위한 JAR파일이다.
 */

/*
    ■ Spring WebFlux의 Reactive Programming(Non-blocking + Back Pressure)
      WebFlux는 Spring Framework v5.0에서 새롭게 추가된 모듈이다.
      기존 Spring은 사용자 요청마다 Thread가 발생하여 Multi-Thread가 당연시 되었다.
      이때 Thread Pool에 Thread가 부족하여 처리율이 저하될 수 있고,
      Thread를 늘려도 메모리 및 CPU 부하(컨텍스스트 스위칭)가 발생할 수 있다.
      만약 정해진 Thread Pool 보다 대기 Queue에 요청이 쌓이면 Latency가 급격히 높아지는 Thread Pool Hell을 야기한다.
      ※ Latency : 한 지점에서(출처) 다른 지점으로(도착) 전송되는 데 소요된 시간(Ex. 버튼 클릭 후 이벤트가 팝업되는 시간)

      문제 해결을 위해 WebFlux는 Single Thread가 최대한의 요청을 처리하도록 하여 최소한의 자원으로 동시성을 핸들링한다.
      따라서 서비스 호출이 잦은 MSA(Micro Service Architecture) 구조에 적합하다.

      ▶ Non-blocking
        기존 Spring은 A 요청 시, 한 Thread가 응답값을 받아 반환하기 전까지 기다리는데 반해
        Spring WebFlux는 A 요청 시, 한 Thread가 응답값을 받기 전까지 기다리지 않고, B 요청을 처리한다.
        여기서 전자가 Blocking 방식이고, 후자가 Non-blocking 방식이다.

      ▶ Back Pressure
        Publisher에서 발행하고, Subscriber에서 구독할 때, Publisher에서 Subscriber로 데이터를 push하는 방식이 아닌
        Subscriber가 pull하는 방식으로 Publisher로부터 처리할 수 있는 데이터 양을 요청 함으로써
        Subscriber에서 발생할 수 있는 장애를 방지하기 위함이다.
        즉, 다이나믹 풀 방식의 데이터 요청을 통해 구독자가 수용할 수 있는 만큼 데이터를 요청하는 방식이다.
 */

/*
    Elasticsearch :
    Logstash :
    Kibana :
    Redis :
    Kafka :
    RabbitMQ :
 */

/*
 */

@RestController
@RequestMapping(value = "/one")
public class OneController extends BaseController {

    // 기본적으로 com.example.javademo.part.com.controller 패키지 내 controller 로깅
    // private static Logger log = (Logger) LoggerFactory.getLogger(OneController.class);

    /*
        * @RequestParam vs @PathVariable
        @RequestParam   : [ http://127.0.0.1?index=1 ]와 같은 형식에서 index 값을 가져오기 위해 사용
        @PathVariable   : [ http://127.0.0.1/index=1 ]와 같은 형식에서 index 값을 가져오기 위해 사용
     */

    // Optional은 Java 8에서 새롭게 도입 된 클래스로, NullPointerException을 방지하고자 출현했다. + 함수형 프로그래밍

    @Autowired
    OneService oneService;

    @Autowired
    MySender mySender;

    @GetMapping
    public String one(@RequestParam(value = "bkNm", required = false, defaultValue = "") String reqBkNm) {
        log.info("test info logger");
        log.debug("test debug logger");

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        try {
            mySender = new MySender(new Sender("You are special."));
            mySender.send();
            System.out.println("is it sent? " + mySender.isSent());
        } catch(ServiceException se) {
            HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HashMap<String, String> errMap = new HashMap<>();
            errMap.put("errCode", se.getRtCd());
            errMap.put("errMsg", se.getRtMsg());
            req.setAttribute("errMap", errMap);
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        return oneService.one(reqBkNm);
    }


//    @GetMapping
//    public String one() {
//        return "good"; // Controller가 String 타입을 반환한다 하더라도 viewResolver에게 view name을 전달한다.
//    }
}

/*
    Plugins > restfultool 플러그인
              ㄴ ctrl + alt + / : Restful URI 바로찾기
    npm autocannon > 성능테스트를 위한 부하 생성
 */