package com.databrick;

import com.databrick.entity.Address;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        logger.info("Sistema iniciado com sucesso!");
        System.out.println();

        Thread.sleep(1000);

        System.out.println("[ " + LocalDateTime.now().format(dtf) + " ] Vamos iniciar a registar as informações no sistema.");

        List<Address> addresses = new ArrayList<>();
        Character continuar;

        do {
            try {
                System.out.println();
                Thread.sleep(500);
                System.out.print("Rua: ");
                Thread.sleep(1000);
                System.out.println("Haddock Lobo");

                Thread.sleep(500);
                System.out.print("Número: ");
                Thread.sleep(1000);
                System.out.println("595");

                Thread.sleep(500);
                System.out.print("Bairro: ");
                Thread.sleep(1000);
                System.out.println("Cerqueira César");

                Thread.sleep(500);
                System.out.print("CEP: ");
                Thread.sleep(1000);
                System.out.println("01414-001");

                LocalDateTime registerDate = LocalDateTime.now();
                addresses.add(new Address(registerDate, "Haddock Lobo", 595, "Cerqueira César", "01414-001"));

                System.out.println();
                System.out.print("[ " + LocalDateTime.now().format(dtf) + " ] Registro concluído. Você deseja cadastrar um novo endereço? (S/N) ");
                continuar = sc.next().toUpperCase().charAt(0);
                sc.nextLine();
            } catch (Exception e) {
                logger.severe(e.getMessage());
                break;
            }

        } while (continuar.equals('S') || continuar.equals('Y'));

        System.out.println();
        System.out.println("[ " + LocalDateTime.now().format(dtf) + " ] As informações foram coletadas com sucesso!");
        System.out.println("[ " + LocalDateTime.now().format(dtf) + " ] Processando...");

        Thread.sleep(2000);
        System.out.println();

        System.out.println("[ " + LocalDateTime.now().format(dtf) + " ] Processamento concluído. Dados obtidos: ");

        System.out.println("+----------------------+----------------------+----------------------+----------------------+----------------------+");
        System.out.printf("| %-20s | %-20s | %-20s | %-20s | %-20s |\n", "Hora do registro", "Rua", "Número", "Bairro", "CEP");
        System.out.println("+----------------------+----------------------+----------------------+----------------------+----------------------+");
        for (Address address : addresses) {
            System.out.printf("| %-20s | %-20s | %-20d | %-20s | %-20s |\n", address.getRegisterDate().format(dtf), address.getRoad(), address.getNumber(), address.getNeighborhood(), address.getZipCode());
            System.out.println("+----------------------+----------------------+----------------------+----------------------+----------------------+");
        }

        System.out.println();
        Thread.sleep(1000);

        logger.info("Sistema finalizado!");
        Thread.sleep(1000);

        sc.close();
    }
}