package com.databrick;

import com.databrick.entity.Address;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        System.out.println("=-----[ " + LocalDateTime.now().format(dtf) + " ]-----=");
        System.out.println("         Sistema iniciado!         ");
        System.out.println("=---------------------------------=");
        System.out.println();

        Thread.sleep(1000);

        System.out.println("[ " + LocalDateTime.now().format(dtf) + " ] Vamos iniciar a registar as informações no sistema.");

        List<Address> addresses = new ArrayList<>();
        Character continuar;

        do {
            try {
                System.out.println();
                System.out.print("Rua: ");
                String road = sc.nextLine();

                System.out.print("Número: ");
                Integer number = sc.nextInt();
                sc.nextLine();

                System.out.print("Bairro: ");
                String neighborhood = sc.nextLine();

                System.out.print("CEP: ");
                String zipCode = sc.nextLine();

                LocalDateTime registerDate = LocalDateTime.now();
                addresses.add(new Address(registerDate, road, number, neighborhood, zipCode));

                System.out.println();
                System.out.print("[ " + LocalDateTime.now().format(dtf) + " ] Registro concluído. Você deseja cadastrar um novo endereço? (S/N) ");
                continuar = sc.next().toUpperCase().charAt(0);
                sc.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
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

        System.out.println("=------[ " + LocalDateTime.now().format(dtf) + " ]------=");
        System.out.println("         Sistema finalizado!         ");
        System.out.println("=-----------------------------------=");

        sc.close();
    }
}