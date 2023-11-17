package com.momo.momoassignment;


import com.momo.momoassignment.service.CommandProcessService;

public class MomoAssignment {
    public static void main(String[] args) {
        new CommandProcessService().processCommand(args);
    }
}
