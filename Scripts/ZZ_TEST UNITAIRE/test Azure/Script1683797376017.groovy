import org.apache.http.HttpRequest
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient

HttpRequest request = HttpRequest.newBuilder()
.uri(URI.create("https://dev.azure.com/mainta/ba23da28-ae09-446a-afac-f19fb6bd8d4e/_apis/wit/workitems/$bug?api-version=7.0"))
.header("Content-Type", "application/json-patch+json")
.header("Authorization", "Basic dWdsMzRibXlzaHc3YXVrc2VxcHVzNW9mYXUzb3Z6N2R0dWpzNmI1NW41NWwzN2loYXdtcTo=")
.method("POST", HttpRequest.BodyPublishers.ofString("[\n\t{\n    \"op\": \"add\",\n    \"path\": \"/fields/System.Title\",\n    \"from\": null,\n    \"value\": \"Test TNR 11052023 bis\"\n  },\n\t{\n\t\t\"op\": \"add\",\n\t\t\"path\": \"/fields/System.State\",\n\t\t\"from\": null,\n\t\t\"value\": \"New\"\n\t},\n\t{\n\t\t\"op\": \"add\",\n\t\t\"path\": \"/fields/System.IterationPath\",\n\t\t\"from\": null,\n\t\t\"value\": \"Héraclès\\\\TNR - TEST\"\n\t},\n\t{\n\t\t\"op\": \"add\",\n\t\t\"path\": \"/fields/System.AreaPath\",\n\t\t\"from\": null,\n\t\t\"value\": \"Héraclès\\\\GMAO\"\n\t}\n]"))
.build();
HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
System.out.println(response.body());
