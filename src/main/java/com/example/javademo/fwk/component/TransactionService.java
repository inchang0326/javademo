package com.example.javademo.fwk.component;

import com.example.javademo.entity.FwkTransactionHst;
import com.example.javademo.entity.FwkTransactionHstId;
import com.example.javademo.repo.jpa.TransactionRepo;
import com.example.javademo.fwk.pojo.CommonArea;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Log // lombok : protected final Logger logAdvice = (Logger) LoggerFactory.getLogger(TransactionService.class)
public class TransactionService {

    @Autowired TransactionRepo repo;

    // 트래픽이 과도하니 save update같이 수행되어 pk dup 발생
    // complatablefuture
    @Async
    public CompletableFuture<FwkTransactionHst> saveTr(CommonArea commons) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("saveTr start");
            FwkTransactionHst tr = convertTr(commons);
            return repo.save(tr);
        });
    }

    @Async
    public void updateTr(CommonArea commons, CompletableFuture<FwkTransactionHst> futureTr) {
        log.info("updateTr start");

        try {
            futureTr.get(); // futureTr 기다림..
        } catch (InterruptedException | ExecutionException e) {
            log.info("futureTr Error!!");
        }

        FwkTransactionHstId id = new FwkTransactionHstId();
        id.setTransactionDate(commons.getTransactionDate());
        id.setAppName(commons.getAppName());
        id.setAppVersion(commons.getAppVersion());
        id.setGid(commons.getGid());
        repo.findById(id); // findById(PK Type > Generic Type)
        repo.save(convertTr(commons));
    }

    /* 대용량 트래픽처리할 때 pk 안넣음 그 이유는 pk 처리가 오래걸리기 때문
    *  안정적 비동기 프로그래밍 처리에 대해
    * */

    public FwkTransactionHst convertTr(CommonArea commons) {
        // FwkTransactionHst init
        FwkTransactionHst newTr = new FwkTransactionHst();
        newTr.setTransactionDate(commons.getTransactionDate());
        newTr.setAppName(commons.getAppName());
        newTr.setAppVersion(commons.getAppVersion());
        newTr.setGid(commons.getGid());
        newTr.setMethod(commons.getMethod());
        newTr.setPath(commons.getPath());
        newTr.setMobYn(commons.getMobYn());

        if (commons.getStartTime() != null)
            newTr.setStartTime(currentTimeByOffset(commons.getStartTime()));
        if (commons.getEndTime() != null)
            newTr.setEndTime(currentTimeByOffset(commons.getEndTime()));

        newTr.setCreateDt(commons.getCreateDt());
        newTr.setUpdateDt(commons.getUpdateDt());

        if (commons.getStartTime() != null && commons.getEndTime() != null) {
            String elapsed = String.valueOf(Duration.between(commons.getStartTime(), commons.getEndTime()).toMillis());
            newTr.setElapsed(elapsed);
        }
        return newTr;
    }

    private String currentTime() {
        LocalDateTime now = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Seoul"));
        return now.format(DateTimeFormatter.ofPattern("HHmmss"));
    }

    private String currentTimeByOffset(OffsetDateTime offset) {
        return offset.format(DateTimeFormatter.ofPattern("HHmmss"));
    }

}
