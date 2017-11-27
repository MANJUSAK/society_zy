package com.goodsoft.society_zy.util;

import org.codehaus.xfire.client.Client;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/11/22.
 */
public class WebServiceTest {
    public static void main(String[] args) {
        try {
            Client client = new Client(
                    new URL(
                            "http://test.lkasp.com:10088/WdWeb/Service1.asmx?WSDL"));
            client.setEndpointUri("http://www.wdweb.com/Run");
            Object[] results = client.invoke("Run",
                    new String[] { "eyJVc2VyTmFtZSI6IkFkbWluIiwiUGFzc3dvcmQiOiJBZG1pbiIsIkNvbUNvZGUiOiIyMDExIiwi\n" +
                            "U3RhcnREYXRlIjoiMjAxNy0xMS0xMiAyMTo0ODo0MyIsIkVuZERhdGUiOiIyMDE3LTExLTEzIDIx\n" +
                            "OjQ4OjQzIn0=" });
            System.out.println(results[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
