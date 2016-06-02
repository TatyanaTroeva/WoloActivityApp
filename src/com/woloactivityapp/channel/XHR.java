package com.woloactivityapp.channel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;

public class XHR {
    private String responseText;
    private Integer status;
    private String statusText;

    /**
     * Handles the Response of a HttpRequest, grab data later by using Getters
     * @param response
     * @throws IOException
     */
    public XHR(HttpResponse response) throws IOException {
        StatusLine statusLine = response.getStatusLine();
        HttpEntity httpEntity = response.getEntity();
        this.status = statusLine.getStatusCode();
        this.statusText = statusLine.getReasonPhrase();
        BufferedReader r = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        this.responseText = total.toString();
    }

    /**
     * Returns the text representation of the status code
     * @return
     */
    public String getStatusText() {
        return this.statusText;
    }

    /**
     * Returns status code of a message
     * @return
     */
    public int getStatus() {
        return this.status;
    }

    /**
     * Returns true on good connection
     * @return
     */
    public boolean isSuccess() {
        return (this.status == HttpStatus.SC_OK);
    }

    /**
     * Return the message from the server
     * @return
     */
    public String getResponseText() {
        return this.responseText;
    }

    /**
     * Override toString Method, makes for nice print statements
     */
    public String toString() {
    	StringBuffer xhr = new StringBuffer();
    	xhr.append("Status: ");
    	xhr.append(this.status);
    	xhr.append(", Error: ");
    	xhr.append(this.statusText);
    	xhr.append(", Message: ");
    	xhr.append(this.responseText);
    	return xhr.toString();
    }
}