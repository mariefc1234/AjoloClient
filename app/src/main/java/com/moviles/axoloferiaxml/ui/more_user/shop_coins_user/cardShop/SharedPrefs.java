package com.moviles.axoloferiaxml.ui.more_user.shop_coins_user.cardShop;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import javax.crypto.Cipher;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import co.infinum.goldfinger.crypto.CipherCrypter;
import co.infinum.goldfinger.crypto.CipherFactory;
import co.infinum.goldfinger.crypto.impl.Base64CipherCrypter;
import co.infinum.goldfinger.crypto.impl.UnlockedAesCipherFactory;

/**
 * Encrypted Shared prefs wrapper which encrypts and decrypts PIN
 * automatically using Goldfinger's exposed API.
 */
public class SharedPrefs {
    private static SharedPreferences PREFS;
    private static CipherCrypter CRYPTER;
    private static CipherFactory FACTORY;

    private static final String PIN = "PIN";
    private static final String IS_RX = "IS_RX";
    private static final String AUTHENTICATORS = "AUTHENTICATORS";

    public static void clear() {
        PREFS.edit().clear().apply();
    }

    @RequiresApi(Build.VERSION_CODES.M)
    public static String getPin() {
        String encryptedPin = PREFS.getString(PIN, "");
        if ("".equals(encryptedPin)) {
            return "";
        }

        Cipher cipher = FACTORY.createDecryptionCrypter(PIN);
        if (cipher == null) {
            return "";
        }

        return CRYPTER.decrypt(cipher, encryptedPin);
    }

    /**
     * Please do not do this in production.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    public static void init(Context context) {
        PREFS = context.getSharedPreferences("My awesome app prefs", Context.MODE_PRIVATE);
        CRYPTER = new Base64CipherCrypter();
        FACTORY = new UnlockedAesCipherFactory(context);
    }

    public static boolean isRxExample() {
        return PREFS.getBoolean(IS_RX, false);
    }

    public static void setPin(String pin) {
        Cipher cipher = FACTORY.createEncryptionCrypter(PIN);
        if (cipher == null) {
            return;
        }

        String encryptedPin = CRYPTER.encrypt(cipher, pin);
        PREFS.edit().putString(PIN, encryptedPin).apply();
    }

    public static void setRxExample(boolean isRxExample) {
        PREFS.edit().putBoolean(IS_RX, isRxExample).commit();
    }

    public static void setAuthenticatorType(int authenticatorType) {
        PREFS.edit().putInt(AUTHENTICATORS, authenticatorType).apply();
    }

    public static int getAuthenticatorType() {
        return PREFS.getInt(AUTHENTICATORS, 0);
    }
}
