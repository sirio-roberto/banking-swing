package banking;

import banking.entities.Card;
import banking.entities.dao.CardDao;
import banking.entities.dao.DaoFactory;

import java.util.Scanner;

public class Main {

    static Scanner scan = new Scanner(System.in);

    static CardDao cardDao;


    public static void main(String[] args) {

        cardDao = DaoFactory.createCardDao(args[1]);
        cardDao.createCardTable();

        // create class to handle this menu
        showInitialOptions();
        int userOption = Integer.parseInt(scan.nextLine());

        while (userOption != 0) {
            switch (userOption) {
                case 1 -> createAccount();
                case 2 -> userOption = logIn();
            }
            if (userOption == 0) {
                break;
            }
            showInitialOptions();
            userOption = Integer.parseInt(scan.nextLine());
        }
        System.out.println("\nBye!");
    }

    private static int logIn() {
        int accountOption = -1;
        System.out.println("\nEnter your card number:");
        String cardNumber = scan.nextLine();
        System.out.println("Enter your PIN:");
        String pin = scan.nextLine();

        Card card = cardDao.findByNumberAndPIN(cardNumber, pin);
        if (card != null) {
            System.out.println("\nYou have successfully logged in!");
            showAccountOptions();
            accountOption = Integer.parseInt(scan.nextLine());
            while (accountOption != 0) {
                switch (accountOption) {
                    case 1 -> System.out.println("\nBalance: " + card.getBalance());
                    case 2 -> addIncome(card);
                    case 3 -> doTransfer(card);
                    case 4 -> closeAccount(card);
                    case 5 -> System.out.println("\nYou have successfully logged out!");
                }
                if (accountOption == 5 || accountOption == 4) {
                    break;
                }
                showAccountOptions();
                accountOption = Integer.parseInt(scan.nextLine());
            }
            System.out.println();
        } else {
            System.out.println("\nWrong card number or PIN!\n");
        }
        return accountOption;
    }

    private static void doTransfer(Card card) {
        System.out.println("\nTransfer");
        System.out.println("Enter card number:");
        String numberToTransfer = scan.nextLine();
        Card cardToTransfer = validateNumberAndReturnCard(numberToTransfer);
        if (cardToTransfer != null) {
            if (!card.equals(cardToTransfer)) {
                System.out.println("Enter how much money you want to transfer:");
                int moneyToTransfer = Integer.parseInt(scan.nextLine());
                if (card.getBalance() >= moneyToTransfer) {
                    int card1Balance = card.getBalance();
                    int card2Balance = cardToTransfer.getBalance();

                    card.setBalance(card1Balance - moneyToTransfer);
                    cardToTransfer.setBalance(card2Balance + moneyToTransfer);
                    if (cardDao.transferMoney(card, cardToTransfer)) {
                        System.out.println("Success!");
                    } else {
                        card.setBalance(card1Balance);
                        cardToTransfer.setBalance(card2Balance);
                    }
                } else {
                    System.out.println("Not enough money!");
                }
            } else {
                System.out.println("You can't transfer money to the same account!");
            }
        } else {
            System.out.println("Such a card does not exist.");
        }
    }

    private static Card validateNumberAndReturnCard(String numberToTransfer) {
        if (Card.isCardNumberValid(numberToTransfer)) {
            return cardDao.findByNumber(numberToTransfer);
        } else {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
        }
        return null;
    }

    private static void closeAccount(Card card) {
        cardDao.delete(card);
        System.out.println("\nThe account has been closed!");
    }

    private static void addIncome(Card card) {
        System.out.println("\nEnter income:");
        int userAmount = Integer.parseInt(scan.nextLine());
        card.addBalance(userAmount);
        cardDao.updateBalance(card);
        System.out.println("Income was added!");
    }

    private static void showAccountOptions() {
        System.out.println("""
                \n1. Balance
                2. Add income
                3. Do transfer
                4. Close account
                5. Log out
                0. Exit""");
    }

    private static void createAccount() {
        Card card = new Card();
        cardDao.insert(card);
        printCreatedMessage(card);
    }

    private static void printCreatedMessage(Card account) {
        System.out.printf("""
                %nYour card has been created
                Your card number:
                %s
                Your card PIN:
                %s%n
                """, account, account.getPIN());
    }

    private static void showInitialOptions() {
        System.out.println("""
                1. Create an account
                2. Log into account
                0. Exit""");
    }
}