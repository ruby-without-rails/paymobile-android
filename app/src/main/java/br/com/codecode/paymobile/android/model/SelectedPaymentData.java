package br.com.codecode.paymobile.android.model;

import java.io.Serializable;

/**
 * Created by felipe on 28/01/18.
 */

public class SelectedPaymentData implements Serializable {

    private String cardHolderName, cardNumber, cardValidity, cardCvv, cardBrand;
    private int cardValidityYear, cardValidityMonth;

    private static final int NONE = 0;
    private static final int VISA = 1;
    private static final int MASTERCARD = 2;
    private static final int DISCOVER = 3;
    private static final int AMEX = 4;

    private SelectedPaymentData() {
    }

    public SelectedPaymentData(String cardHolderName, String cardNumber, String cardValidity, String cardCvv, int cardBrand) {
        this();
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cardValidity = cardValidity;
        this.cardCvv = cardCvv;
        String[] strings = cardValidity.split("/");
        this.cardValidityMonth = Integer.parseInt(strings[0]);
        this.cardValidityYear = Integer.parseInt(strings[1]);
        this.cardBrand = discoverCardBrand(cardBrand);
    }

    private String discoverCardBrand(int cardBrand) {
        String brand;
        switch (cardBrand) {
            case 1: {
                brand = "Visa";
                break;
            }
            case 2: {
                brand = "MasterCard";
                break;
            }
            case 3: {
                brand = "Discover";
                break;
            }
            case 4: {
                brand = "Amex";
                break;
            }
            default: {
                brand = "None";
                break;
            }
        }
        return brand;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardValidity() {
        return cardValidity;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public int getCardValidityYear() {
        return cardValidityYear;
    }

    public int getCardValidityMonth() {
        return cardValidityMonth;
    }

    public String getCardBrand() {
        return cardBrand;
    }
}
