package banking.entities;

import java.util.Arrays;
import java.util.Random;

public class Card {
    private String BIN;
    private String accountIdentifier;
    private String checkSum;
    private String PIN;
    private Integer balance;

    public Card() {
        this.BIN = "400000";
        this.accountIdentifier = generateAccountIdentifier();
        this.checkSum = generateCheckSum();
        this.PIN = generatePIN();
        this.balance = 0;
    }

    public Card(String number, String PIN, int balance) {
        this.BIN = number.substring(0, 6);
        this.accountIdentifier = number.substring(6, 14);
        this.checkSum = number.substring(14);
        this.PIN = PIN;
        this.balance = balance;
    }

    public String getPIN() {
        return PIN;
    }

    private String generatePIN() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(0, 10000));
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    private String generateCheckSum() {
        Integer[] cardNumberWithoutCheckSum = Arrays.stream(String.format("%s%s", BIN, accountIdentifier)
                .split(""))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);

        int sum = 0;
        for (int i = 0; i < cardNumberWithoutCheckSum.length; i++) {
            if (i % 2 == 0) {
                cardNumberWithoutCheckSum[i] *= 2;
            }
            if (cardNumberWithoutCheckSum[i] > 9) {
                cardNumberWithoutCheckSum[i] -= 9;
            }
            sum += cardNumberWithoutCheckSum[i];
        }

        return String.valueOf((sum * 10 - sum % 10) % 10);
    }

    public void addBalance(int userAmount) {
        this.balance += userAmount;
    }

    private String generateAccountIdentifier() {
        Random random = new Random();
        return String.format("%09d", random.nextInt(0, 1000000000));
    }

    @Override
    public String toString() {
        return String.format("%s%s%s", BIN, accountIdentifier, checkSum);
    }

    public boolean equals(Card other) {
        return this.toString().equals(other.toString());
    }

    public static boolean isCardNumberValid(String cardNumber) {
        if (!cardNumber.matches("\\d+")) {
            return false;
        }
        int checkSum = Integer.parseInt(cardNumber.substring(cardNumber.length() - 1));
        int[] intArray = Arrays.stream(cardNumber.substring(0, cardNumber.length() - 1).split(""))
                .mapToInt(Integer::parseInt).toArray();
        for (int i = 0; i < intArray.length; i++) {
            if (i % 2 == 0) {
                intArray[i] *= 2;
                if (intArray[i] > 9) {
                    intArray[i] -= 9;
                }
            }
            checkSum += intArray[i];
        }
        return checkSum % 10 == 0;
    }
}
