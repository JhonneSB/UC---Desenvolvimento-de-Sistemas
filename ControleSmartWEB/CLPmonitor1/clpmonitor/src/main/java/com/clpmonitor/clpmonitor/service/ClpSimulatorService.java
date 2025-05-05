package com.clpmonitor.clpmonitor.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.clpmonitor.CLP.PlcConnector;
import com.clpmonitor.clpmonitor.model.ClpData;

import jakarta.annotation.PostConstruct;

@Service
public class ClpSimulatorService {

    public static byte[] indexColorEst = new byte[28];

    public PlcConnector plcEstDb9;
    public PlcConnector plcExpDb9;

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);

    @PostConstruct
    public void startSimulation() {
        sendClp1Update();
        sendClp2to3Updates();
        sendClp4Update();
        // executor.scheduleAtFixedRate(this::sendClp1Update, 0, 3800, TimeUnit.MILLISECONDS);
        // executor.scheduleAtFixedRate(this::sendClp2to3Updates, 0, 3, TimeUnit.SECONDS);
        // executor.scheduleAtFixedRate(this::sendClp4Update, 0, 3800, TimeUnit.MILLISECONDS);
    }

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(0L);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        return emitter;
    }

    private void sendClp1Update() {
        plcEstDb9 = new PlcConnector("10.74.241.10", 102);
        try {
            plcEstDb9.connect();
            indexColorEst = plcEstDb9.readBlock(9, 68, 28);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        List<Integer> byteArray = new ArrayList<>();
        for (int i = 0; i < 28; i++) {
            byteArray.add((int) indexColorEst[i]);
        }

        ClpData clp1 = new ClpData(1, byteArray);
        sendToEmitters("clp1-data", clp1);
    }

    private void sendClp2to3Updates() {
        Random rand = new Random();
        sendToEmitters("clp2-data", new ClpData(2, rand.nextInt(100)));
        sendToEmitters("clp3-data", new ClpData(3, rand.nextInt(100)));
    }

    private void sendClp4Update() {
        int[] values = new int[12];

        plcExpDb9 = new PlcConnector("10.74.241.40", 102);
        try {
            plcExpDb9.connect();
            int j = 0;
            for (int i = 6; i <= 28; i += 2) {
                values[j] = plcExpDb9.readInt(9, i);
                j++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        List<String> expeditionData = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            if (values[i] == 0) {
                expeditionData.add("P" + (i + 1) + "= [ _ ]");
            } else {
                expeditionData.add("P" + (i + 1) + "= [OP" + String.format("%04d", values[i]) + "]");
            }
        }

        ClpData clp4 = new ClpData(4, expeditionData);
        sendToEmitters("clp4-data", clp4);
    }

    private void sendToEmitters(String eventName, ClpData clpData) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name(eventName).data(clpData));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
        }
    }
}
