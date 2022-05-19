/*
 Copyright (C) 2021-2022 MCUmbrella & contributors
 Licensed under the MIT License. See LICENSE in the project root for license information.
*/

package vip.floatationdevice.guilded4j.rest;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import vip.floatationdevice.guilded4j.enums.ExceptionType;
import vip.floatationdevice.guilded4j.exception.GuildedException;

/**
 * Class for interacting with the Guilded REST API.
 */
public abstract class RestManager
{
    String authToken;
    int httpTimeout = 20000;

    public RestManager(String authToken){this.authToken = authToken;}

    /**
     * Set the authorization token to be used for HTTP requests.
     * @param token The auth token.
     */
    public void setAuthToken(String token){this.authToken = token;}

    /**
     * Set the timeout of the HTTP request.
     * @param timeoutMs The timeout in milliseconds.
     */
    public RestManager setHttpTimeout(int timeoutMs)
    {
        this.httpTimeout = timeoutMs;
        return this;
    }

    /**
     * Execute a HTTP request.
     * @param method The HTTP method.
     * @param url The URL to request.
     * @param body The body of the request (if any).
     * @return The response JSON object. Null if no response body was returned.
     * @throws GuildedException If Guilded returns an error.
     * @throws cn.hutool.core.io.IORuntimeException if an error occurred while sending HTTP request.
     */
    public JSONObject execute(Method method, String url, JSONObject body)
    {
        HttpRequest req = new HttpRequest(UrlBuilder.of(url))
                .method(method)
                .header("Authorization", "Bearer " + authToken)
                .header("Accept", "application/json")
                .header("Content-type", "application/json")
                .timeout(httpTimeout);
        if(body != null) req.body(body.toString());
        HttpResponse res = req.execute();
        switch(res.getStatus())
        {
            case 200:
            case 201:
                return new JSONObject(res.body());
            case 204:
                return null;
            default:
                throw GuildedException.fromString(res.body()).setType(ExceptionType.fromInt(res.getStatus()));
        }
    }
}
