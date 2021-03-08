package com.example.javademo.part.com.controller;

import com.example.javademo.entity.Tmp;
import com.example.javademo.fwk.base.BaseController;
import com.example.javademo.repo.jpa.TmpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Iterator;

@RestController
@RequestMapping(value = "/tmps")
public class TmpController extends BaseController {

    @Autowired
    TmpRepo tmpRepo;

    @GetMapping
    public  ArrayList<Tmp> getAll() {
        Iterable<Tmp> it = tmpRepo.findAll();
        Iterator<Tmp> iterator = it.iterator();

        ArrayList<Tmp> list = new ArrayList<>();
        while(iterator.hasNext()) {
            list.add(iterator.next());
        }

        log.info(ca.getTransactionDate().toString());
        log.info(ca.getAppName());
        log.info(ca.getAppVersion());
        log.info(ca.getGid());
        log.info(ca.getPath());
        log.info(ca.getMethod());
        log.info(ca.getRemoteIp());
        log.info(ca.getHostName());
        log.info(String.valueOf(ca.isBLocal()));
        log.info(String.valueOf(ca.isBDev()));
        log.info(String.valueOf(ca.isBPrd()));
        log.info(ca.getMobYn());

        return list;
    }


}