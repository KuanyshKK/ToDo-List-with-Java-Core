package com.company;

import com.company.exam.ExamServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            new ExamServer("localhost", 8080).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
