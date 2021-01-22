package com.leetcode.handwrite.future;

import java.util.List;

public class SearchResult {
    private final String keyword;
    private final List<String> records;
    private final long startTime;
    private final long endTime;
    SearchResult(String keyword, List<String> records, long startTime, long endTime) {
        this.keyword = keyword;
        this.records = records;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public String getKeyword() {
        return keyword;
    }
    public List<String> getRecords() {
        return records;
    }
    public long getStartTime() {
        return startTime;
    }
    public long getEndTime() {
        return endTime;
    }
    public long getTotalTime() {
        return endTime - startTime;
    }
}
