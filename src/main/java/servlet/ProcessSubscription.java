package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProcessSubscription", urlPatterns = { "/Subscription" })
public class ProcessSubscription extends HttpServlet {
	
	/**
	 * @param args
	 */
	
	static String app_Secret = "b6e08769dc3d3421f9677855ba852013";
	static String app_Id = "521073994746729";
	static String app_AccessToken = "521073994746729|ZupZl-GaTXZO9cwrogu88U16NFA";
	
	/*static{
//		https://graph.facebook.com//oauth/access_token?client_id=<app-id>&client_secret=<app-secret>&grant_type=client_credentials
//		https://graph.facebook.com//oauth/access_token?client_id=521073994746729&client_secret=b6e08769dc3d3421f9677855ba852013&grant_type=client_credentials
		String url = "https://graph.facebook.com//oauth/access_token?client_id="+ app_Id +"&client_secret=" + app_Secret + "&grant_type=client_credentials";
		try {
			URL urldemo = new URL(url);
			URLConnection uc = urldemo.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				System.out.println("app accessToken : "+inputLine);
			app_AccessToken = inputLine;
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}*/
	
	@Override
	public void init() throws ServletException {
		String url = "https://graph.facebook.com//oauth/access_token?client_id="+ app_Id +"&client_secret=" + app_Secret + "&grant_type=client_credentials";
		try {
			URL urldemo = new URL(url);
			URLConnection uc = urldemo.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				System.out.println("app accessToken : "+inputLine);
			app_AccessToken = inputLine;
			in.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Inside service");

		super.service(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Inside doGet");
		super.doGet(req, resp);
	}
	
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside doPost");
		/* GET /oauth/access_token?  
	    grant_type=fb_exchange_token&amp;           
	    client_id={app-id}&amp;
	    client_secret={app-secret}&amp;
	    fb_exchange_token={short-lived-token}  */
	    /* app-id:521073994746729
	    secret:b6e08769dc3d3421f9677855ba852013 */
		
		String pageId = request.getParameter("page_id");
		System.out.println("Inside doPost 2");
		System.out.print("pageId:"+pageId);
        String page_accessToken = request.getParameter("access_token");
        System.out.println("Inside doPost 3");
        System.out.println("Inside doPost 4");
        System.out.print("page access token:"+page_accessToken);
		
        if(app_AccessToken != null && page_accessToken != null){
//        	https://graph.facebook.com/debug_token?input_token=<page-short-live-access-token>&access_token=<app-accesstoken>
        	try {
				String url = "https://graph.facebook.com/debug_token?input_token="+ page_accessToken +"&access_token=" + app_AccessToken;
				URL urldemo = new URL(url);
				URLConnection uc = urldemo.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
				String inputLine;
				while ((inputLine = in.readLine()) != null)
					System.out.println("debug the page access token"+inputLine);
				
//				page access token:EAAHZA6f5by2kBAIcKsATGS6cITgZBzEocTXt390tTVqcmDhLKBMklc3W4JWIU8zdTg7RARr22Mb3aaCsYrKrF6ZBZBN9caqdZCAw8AZAZBBCvSRx94AZAmtGnVsLVtc4xjobFS8rKE5X8eBpEw0btM30LdxvKnTDynJOEstNZABIrcNkyZBfbLZA4ik
//				pageId:1376204942612961
				/*debug the page access token
				 * {"data":{	"app_id":"521073994746729",
								"application":"Lead Generating App",
								"expires_at":1464134400,
								"is_valid":true,
								"profile_id":"1376204942612961",
								"scopes":["manage_pages","public_profile"],
								"user_id":"1137096926311693"}}*/
				
				in.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        }

/*        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();*/
      /*  
        if(page_acessToken != null){
//        	https://graph.facebook.com/oauth/client_code?access_token=...&amp;client_secret=...&amp;redirect_uri=...&amp;client_id=...
        	String url = "https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&amp;client_id="+ app_Id +"&amp;client_secret=" + app_Secret + "&amp;fb_exchange_token=" + page_acessToken;
			URL urldemo = new URL(url);
			URLConnection yc = urldemo.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null)
				System.out.println(inputLine);
			in.close();        
		}*/
	}

}
