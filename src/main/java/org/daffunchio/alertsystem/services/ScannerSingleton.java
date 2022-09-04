package org.daffunchio.alertsystem.services;

import java.util.Scanner;

public class ScannerSingleton {
    private static final Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }
}
