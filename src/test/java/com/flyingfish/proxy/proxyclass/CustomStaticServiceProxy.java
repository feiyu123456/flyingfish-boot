package com.flyingfish.proxy.proxyclass;

import com.flyingfish.proxy.interfacecustom.StudyService;
import com.flyingfish.proxy.interfacecustom.WorkService;

public class CustomStaticServiceProxy implements StudyService, WorkService {

    private StudyService studyService;
    private WorkService workService;

    public CustomStaticServiceProxy(StudyService studyService) {
        this.studyService = studyService;
    }

    public CustomStaticServiceProxy(WorkService workService) {
        this.workService = workService;
    }

    @Override
    public void read() {
        studyService.read();
    }

    @Override
    public void write() {
        studyService.write();
    }

    @Override
    public void code() {
        workService.code();
    }

    @Override
    public void check() {
        workService.check();
    }
}
