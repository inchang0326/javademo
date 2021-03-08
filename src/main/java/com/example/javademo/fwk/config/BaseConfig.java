package com.example.javademo.fwk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;

import java.util.concurrent.Executor;

@Configuration // Configuration 파일이므로, 스프링 동작 시 가장 먼저 업로드 되도록 한다.
@EnableAspectJAutoProxy(proxyTargetClass = true) // 스프링이 요청 메소드 호출 시점을 자동으로 가로챌 수 있게 해주는 옵션
                                                 // Java Config 방법. xml에서도 설정 가능하다.
public class BaseConfig {
    /*
        ■ Template Engine이란?
        - 웹 소스코드(ex. html)에 DB Conn을 통해 갖고 온 데이터를 결합하여 결과를 출력해주는 소프트웨어로,
          둘 사이 결합성을 낮춤으로써 유지보수를 용이하게 한다.
          Ex) A.view에 새롭게 추가된 Z 칼럼 데이터를 보여줘야할 때,
              JSTL로 for each 구문을 구현했다면 A.view 소스코드는 변경이 필요하지 않다.
        - Thymeleaf, JSP(Java Server Page), Handlebar 등
        - 서버 사이드 Template Engine이 관행이었으나, 페이지 일부가 바뀌면(Ex. Map App에서 좌표 데이터가 바뀜)
          전체 페이지를 다시 그려야하는(reload) 문제가 발생했다. 따라서 Ajax(Asynchronous JavaScript and XML) 통신이
          개발되어 바뀐 부분만 재가공하여 보여주게 되는데, 조금의 실수라도 페이지가 보이지 않거나 이상하게 보이는 문제가 발생한다.
          따라서 클라이언트 사이드 Template Engine이 출현했다.

        ■ 서버/클라이언트 사이드 Template Engine
        - 서버 사이드 Template Engine은 서버에서 View 생성 후 렌더링까지 수행한 다음 클라이언트로 송신한다.
          Ex) Thymeleaf - JSP&JSTL과 유사함
          ① 사용자 요청
          ② 데이터 수급(Restful API)
             json : {Data:{{"1","A"},{"2","B"},{"3","C"}}
          ③ 관련 HTML 코드 생성
             <html>
               <head>
               </head>
               <body>
                 <div class="container">
                   <table class="table">
                     <tr class="table-success">
                       <th>#</th>
                       <th>bean</th>
                     </tr>
                     <tr th:each="bean, it : ${beans}">
                       <td th:text="${it.index}"></td>
                       <td th:text="${bean}"></td>
                     </tr>
                   </table>
                 </div>
               </body>
             </html>
          ④ 서버 렌더링
             <tr>
               <td>1</td>
               <td>A</td>
             </tr>
             <tr>
               <td>2</td>
               <td>B</td>
             </tr>
             <tr>
               <td>3</td>
               <td>C</td>
             </tr>
             ...
          ⑤ 클라이언트 송신
        - 클라이언트 사이드 Template Engine은 서버에서 데이터만 받고 클라이언트에서 View 생성과 렌더링 모두를 수행한다.
          Ex) Angular.js React.js Vue.js, Handlebar.js 등
          ① 고객 요청
          ② HTML 코드 생성
          ③ 서버로 API 요청 및 데이터 수신
          ④ 클라이언트 렌더링
     */

    @Bean
    public SpringResourceTemplateResolver templateResolver()  {
        SpringResourceTemplateResolver templateResolver= new SpringResourceTemplateResolver();
        templateResolver.setPrefix("classpath:/views/");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine()  {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    // Async 처리 관련
    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("thread-"); // log 확인
        executor.initialize();
        return executor;
    }
}
