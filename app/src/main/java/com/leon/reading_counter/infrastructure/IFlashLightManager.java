package com.leon.reading_counter.infrastructure;

/**
 * Created by Leon on 2/18/2018.
 */

public interface IFlashLightManager {
    boolean turnOn();

    boolean turnOff();

    boolean toggleFlash();

    void flashLightOn();

    void flashLightOff();
}
