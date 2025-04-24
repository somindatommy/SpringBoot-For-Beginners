import ballerina/http;
import ballerina/log;

public function main() returns error? {
    // Step 1: Token request client
    http:Client tokenClient = check new ("https://api.asgardeo.io/t/integratechoreo/oauth2");

    // Token request payload and headers
    http:Request req = new;
    req.setHeader("Authorization", "Basic STN1WGFlZjBfZXdGTVdnQ0x0a2lNRmxNVG9zYTpHSjhRSTB5R2htMkoxbEpfQWJZVTFld0xCNFlteHZmcjFMMV9vR1dyTTB3YQ==");
    req.setHeader("Content-Type", "application/x-www-form-urlencoded");

    string formBody = "grant_type=password" +
                      "&scope=payments_init payments_list openid" +
                      "&username=cashier@mnc.com&password=Sominda@1234";
    req.setTextPayload(formBody, contentType = "application/x-www-form-urlencoded");

    // Step 2: Make the token request
    http:Response tokenResponse = check tokenClient->post("/token", req);
    json tokenPayload = check tokenResponse.getJsonPayload();

    if tokenPayload is map<json> && tokenPayload.hasKey("access_token") {
        string accessToken = <string>tokenPayload["access_token"];
        log:printInfo("✅ Access Token acquired");

        // Step 3: Make second API call with the access token
        string paymentAPIBaseUrl = "https://e9235cb9-3185-415e-9469-dd6343d6c4b1-prod.e1-us-east-azure.choreoapis.dev";
        http:Client apiClient = check new (paymentAPIBaseUrl);

        // Prepare headers for the GET request
        map<string> headers = {
            "Authorization": "Bearer " + accessToken,
            "accept": "*/*"
        };

        string endpointPath = "/default/payment-manager/v1.0/api/v1/payments";

        // Step 4: Make the GET request using execute() to include custom headers
        http:Response apiResp = check apiClient->get(endpointPath, headers);
        string apiResponseText = check apiResp.getTextPayload();

        // Get the status code directly from the response object
        int statusCode = apiResp.statusCode;

        // Check if the response is successful
        if statusCode == 200 {
            log:printInfo("💬 Payment API Response: " + apiResponseText);
        } else {
            log:printError("❌ Failed to get valid response. Status code: " + statusCode.toString());
        }
    } else {
        log:printError("❌ access_token not found in response. Exiting.");
        return;
    }
}
