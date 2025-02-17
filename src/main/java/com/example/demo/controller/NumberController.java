package com.example.demo.controller;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.PriorityQueue;

@RestController
@RequestMapping("/api")
public class NumberController {

    @GetMapping
    public Integer getNthMax(@RequestParam String filePath, @RequestParam int n) throws IOException {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(n);

        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                int number = (int) row.getCell(0).getNumericCellValue();
                if (minHeap.size() < n) {
                    minHeap.offer(number);
                } else if (number > minHeap.peek()) {
                    minHeap.poll();
                    minHeap.offer(number);
                }
            }
        }
        return minHeap.peek();
    }
}