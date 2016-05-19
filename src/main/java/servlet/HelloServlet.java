package servlet;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet(name = "HelloServlet", urlPatterns = { "/webhook" })
public class HelloServlet extends HttpServlet {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String jsonRequest = "{\"entry\": [{\"changes\": [{\"field\": \"leadgen\", \"value\": {\"ad_id\": 0, \"form_id\": 945245845529885, \"leadgen_id\": 974394229281713, \"created_time\": 1463468994, \"page_id\": 1376204942612961, \"adgroup_id\": 0}}], \"id\": \"1376204942612961\", \"time\": 1463468994}], \"object\": \"page\"}";
		System.out.println("request: "+jsonRequest);
		JsonParser  jsonParser = new JsonParser();
		JsonElement jsonElt = jsonParser.parse(jsonRequest);
		
		if(jsonElt.isJsonObject()){
			JsonObject jsonObj = (JsonObject) jsonElt;
			JsonArray entry = (JsonArray) jsonObj.get("entry");
			jsonElt = jsonObj.get("object");
			
			System.out.println("object: "+jsonElt.toString());
			
			
			GsonBuilder builder = new GsonBuilder();
//			builder.setDateFormat("yyyy-MM-dd hh:mm:ss");
			Gson gson = builder.create();
			fbEntry[] leadList = gson.fromJson(entry, fbEntry[].class);
			System.out.println("leadList :"+leadList);
			if(leadList == null || leadList.length == 0)
				System.out.println("No leads!");
			else{
				System.out.println("Leads="+leadList.length);
				
				for(fbEntry lead : leadList){
					String externalSourceData = gson.toJson(lead);
					System.out.println("lead request view: "+externalSourceData);
//					externalSourceData = externalSourceData.replaceAll("\":[\"]?", "=");
//					externalSourceData = externalSourceData.replaceAll("[\"]?,\"", "<br>");
//					externalSourceData = externalSourceData.replace("{\"", "");
//					externalSourceData = externalSourceData.replace("\"}", "");
//					System.out.println("lead request view: "+externalSourceData);
				}
			}
		}else{
			System.out.println("Error: JSON is not an array");
//			JsonObject jObj = (JsonObject) jsonParser.parse(jsonRequest);
//			JsonArray jAry = (JsonArray) jsonParser.parse(jsonRequest);
//			System.out.println("jObj: "+jObj);
//			System.out.println("object: "+jObj.get("object"));
//			System.out.println("Entry: "+jObj.get("entry"));
//			System.out.println("Entry: "+jObj.get("entry"));
			
//			Gson gson = new Gson();
//			CFError error = gson.fromJson(response, CFError.class);
//			System.out.println(error.getStatus()+","+error.getStatus_code()+","+error.getMessage());
//			throw new HomeBuy360Exception("Error Details="+response, HomeBuy360Exception.EXCEPTION);
		}
	}
	
//	private static class fbRequest{
//		private fbEntry[] entry;
//		private String object;
		
		private static class fbEntry{
			private fbChanges[] changes;
			private String id;
			private String time;
			
			private static class fbChanges{
				private String field;
				private fbValues value;
				
				private static class fbValues{
					private String ad_id;
					private String form_id;
					private String leadgen_id;
					private String created_time;
					private String page_id;
					private String adgroup_id;
				}
			}
		}

/*		public fbEntry[] getEntry() {
			return entry;
		}

		public void setEntry(fbEntry[] entry) {
			this.entry = entry;
		}

		public String getObject() {
			return object;
		}

		public void setObject(String object) {
			this.object = object;
		}
		
	}*/
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String challenge = "hub.challenge";
		String token = "hub.verify_token";
		String challengeValue = req.getParameter(challenge);
		String tokenValue = req.getParameter(token);
		ServletOutputStream out = resp.getOutputStream();
//		PrintWriter out = resp.getWriter();
		if(tokenValue.equals("abc123")){
			out.write(challengeValue.getBytes());
			System.out.println("challenge: "+challengeValue);
		}
		out.flush();
		out.close();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader bufferedReader = request.getReader();
		char[] charBuffer = new char[128];
		StringBuilder stringBuilder = new StringBuilder();
		try {
			if(bufferedReader != null){
				int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
			}
		} catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    }
		
		System.out.println("request came:"+stringBuilder.toString());
		
		String jsonRequest = stringBuilder.toString();
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElt = jsonParser.parse(jsonRequest);
		
		if(jsonElt.isJsonArray()){
			GsonBuilder builder = new GsonBuilder();
//			builder.setDateFormat("yyyy-MM-dd hh:mm:ss");
			Gson gson = builder.create();
			fbEntry[] leadList = gson.fromJson(jsonRequest, fbEntry[].class);
			if(leadList == null || leadList.length == 0)
				System.out.println("No leads!");
			else{
				System.out.println("Leads="+leadList.length);
				
				for(fbEntry lead : leadList){
					String externalSourceData = gson.toJson(lead);
					System.out.println("lead request view: "+lead);
//					externalSourceData = externalSourceData.replaceAll("\":[\"]?", "=");
//					externalSourceData = externalSourceData.replaceAll("[\"]?,\"", "<br>");
//					externalSourceData = externalSourceData.replace("{\"", "");
//					externalSourceData = externalSourceData.replace("\"}", "");
					
				}
			}
		}else{
			System.out.println("Error: JSON is not an array");
//			Gson gson = new Gson();
//			CFError error = gson.fromJson(response, CFError.class);
//			System.out.println(error.getStatus()+","+error.getStatus_code()+","+error.getMessage());
//			throw new HomeBuy360Exception("Error Details="+response, HomeBuy360Exception.EXCEPTION);
		}
	}

}
