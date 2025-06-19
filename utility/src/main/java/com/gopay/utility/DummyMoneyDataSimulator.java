package com.gopay.utility;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// 지속적으로 특정 지역('구')에 대한 머니 총액 값
// 지속적으로 충분한 고객들이 머니를 충전하고, 결제하는 시뮬레이터
public class DummyMoneyDataSimulator {

    private static final String DECREASE_API_ENDPOINT = "http://localhost:8083/money/decrease-eda";
    private static final String INCREASE_API_ENDPOINT = "http://localhost:8083/money/increase-eda";

    private static final String CREATE_MONEY_API_ENDPOINT = "http://localhost:8083/money/create-member-money";
    private static final String REGISTER_ACCOUNT_API_ENDPOINT = "http://localhost:8082/banking/account/register-eda";

    private static final String[] BANK_NAME = {"KB", "신한", "우리"};
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        List<Integer> readyMemberList = new ArrayList<>();

        // 무한 루프
        while (true) {
            // 증액 머니, 감액 머니
            int amount = random.nextInt(20001) + 1 ; // 0 ~ 20,000원을 증액 또는 감액 랜덤하게 생성
            int targetMembershipId = random.nextInt(10001) + 1; // 1 ~ 10,000 사이의 유저 id 랜덤하게 생성

            //

            registerAccountSimulator(REGISTER_ACCOUNT_API_ENDPOINT, targetMembershipId); // 계좌 생성
            createMemberMoneySimulator(CREATE_MONEY_API_ENDPOINT, targetMembershipId); // 머니 정보 생성


            // 비동기 작업이 db까지 반영되기를 기다리는 시간
            Thread.sleep(100);

            // 증액, 감액 요청을 받을 준비된 고객
            readyMemberList.add(targetMembershipId);
            // 증액
            increaseMemberMoneySimulator(INCREASE_API_ENDPOINT, amount, targetMembershipId);
            amount = random.nextInt(20001) + 1 ; // 0 ~ 20,000원을 증액 또는 감액 랜덤하게 생성

            // 준비된 고객 리스트 중 랜덤하게 1명에게 감액 요청
            Integer decreaseTargetMembershipId = readyMemberList.get(random.nextInt(readyMemberList.size()));
            increaseMemberMoneySimulator(DECREASE_API_ENDPOINT, amount, decreaseTargetMembershipId);

            try {
                Thread.sleep(100); // Wait for
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void increaseMemberMoneySimulator(String apiEndpoint, int amount, int targetMembershipId) {
        try {
            URL url = new URL(apiEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject jsonRequestBody = new JSONObject();
            jsonRequestBody.put("amount", amount);
            jsonRequestBody.put("targetMembershipId", targetMembershipId);

            call(apiEndpoint, conn, jsonRequestBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void registerAccountSimulator(String apiEndpoint, int targetMembershipId) {
        try {
            URL url = new URL(apiEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            Random random = new Random();

            JSONObject jsonRequestBody = new JSONObject();
            jsonRequestBody.put("bankAccountNumber", generateRandomAccountNumber());
            jsonRequestBody.put("bankName", BANK_NAME[random.nextInt(BANK_NAME.length)]);
            jsonRequestBody.put("membershipId", targetMembershipId);
            jsonRequestBody.put("valid", true);

            call(apiEndpoint, conn, jsonRequestBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createMemberMoneySimulator(String apiEndpoint, int targetMembershipId) {
        try {
            URL url = new URL(apiEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject jsonRequestBody = new JSONObject();
            jsonRequestBody.put("membershipId", targetMembershipId);

            call(apiEndpoint, conn, jsonRequestBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void call(String apiEndpoint, HttpURLConnection conn, JSONObject jsonRequestBody) throws IOException {
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(jsonRequestBody.toString().getBytes());
        outputStream.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        System.out.println("API Response from " + apiEndpoint + ": " + response.toString());
    }

    private static String generateRandomAccountNumber() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10); // Generate a random digit (0 to 9)
            sb.append(digit);
        }

        return sb.toString();
    }
}