package com.nie.service.dataLoad.update;

/**
 * @author zhaochengye
 * @date 2019-04-22 14:37
 */
public abstract class AbstractDataUpdateChecker implements DataUpdateChecker {

    // Checker Name
    private String checkerName;
    // DB 检测状态
    protected long lastCheckTime = 0L;

    public long getLastCheckTime() {
        return lastCheckTime;
    }

    public void setLastCheckTime(long lastCheckTime) {
        this.lastCheckTime = lastCheckTime;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    @Override
    public boolean shouldCheck() {
        Long now = System.currentTimeMillis();
        if (now >= getLastCheckTime() + getCheckInterval())
            return true;

        return false;
    }

    public Long getCheckInterval() {
        // 默认周期设为一天
        Long interval = 24L * 60 * 60 * 1000;
//        try {
//            if (config != null) {
//                interval = (long) (config.getMinute()) * 60 * 1000;
//            } else {
//                log.info("Config is not init for checker " + checkerName);
//            }
//        } catch (Exception e) {
//            log.error("Failed to get config for checker " + checkerName, e);
//        }
        return interval;
    }

    public abstract void init();
}
