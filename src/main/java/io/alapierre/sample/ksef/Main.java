package io.alapierre.sample.ksef;

import eu.europa.esig.dss.token.PasswordInputCallback;
import eu.europa.esig.dss.token.PrefilledPasswordCallback;
import io.alapierre.crypto.dss.signer.CardSigner;
import io.alapierre.crypto.dss.signer.P12Signer;
import io.alapierre.ksef.api.dss.facade.KsefDssFacade;
import io.alapierre.ksef.client.AbstractApiClient;
import io.alapierre.ksef.client.ApiClient;
import io.alapierre.ksef.client.ApiException;
import io.alapierre.ksef.client.JsonSerializer;
import io.alapierre.ksef.client.api.InterfejsyInteraktywneSesjaApi;
import io.alapierre.ksef.client.model.rest.auth.AuthorisationChallengeRequest;
import io.alapierre.ksef.client.model.rest.auth.InitSignedResponse;
import io.alapierre.ksef.client.okhttp.OkHttpApiClient;
import io.alapierre.ksef.client.serializer.gson.GsonJsonSerializer;

import java.io.File;
import java.io.IOException;
import java.security.KeyStore;

import static io.alapierre.ksef.client.AbstractApiClient.Environment.TEST;
import static io.alapierre.ksef.client.model.rest.auth.AuthorisationChallengeRequest.IdentifierType.*;

/**
 * @author Adrian Lapierre {@literal al@alapierre.io}
 * Copyrights by original author 2024.01.01
 */
public class Main {

    private static final JsonSerializer serializer = new GsonJsonSerializer();
    private static final ApiClient client = new OkHttpApiClient(serializer, TEST);
    private static final InterfejsyInteraktywneSesjaApi sesjaApi = new InterfejsyInteraktywneSesjaApi(client);

    public static void main(String[] args) throws IOException, ApiException {

        String tokenFile = "src/main/resources/stamp.p12";
        String nip = "2932110194";

        P12Signer signer = new P12Signer(new KeyStore.PasswordProtection("123ewqasd".toCharArray()), new File(tokenFile));

//        if you want to use real electronic stamp, uncomment this
//        PasswordInputCallback callback = new PrefilledPasswordCallback(new KeyStore.PasswordProtection("......".toCharArray()));
//        CardSigner signer = new CardSigner("/opt/proCertumSmartSign", "cryptoCertum3PKCS", 1, callback);

        KsefDssFacade facade = new KsefDssFacade(signer, sesjaApi);
        InitSignedResponse res = facade.authByDigitalSignature(nip, onip);

        System.out.printf("session token = %s", res.getSessionToken());

    }

}
