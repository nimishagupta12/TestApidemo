import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class TestRestAssured {
    static ExtentTest extent1;
    static ExtentReports reports;
    @BeforeClass
    public void setUp() {
        reports =new ExtentReports("C:/DRE - Main Branch/Test/src/test/etr.html",true);
        RestAssured.requestSpecification = new RequestSpecBuilder().setBaseUri("https://jsonplaceholder.typicode.com/").build()
                .accept("ContentType.JSON");

    }
    @DataProvider(name="checkData")
    public Object[][] checkData()
    {
        return new Object[][]
                {
                        {"userId", 1},
                        {
                                "id", 1
                        },
                        {
                                "title", "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
                        },
                        {
                                "body", "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
                        }
                };
    }

    @Test(dataProvider ="checkData" )
    public void getUp(String key,Object value)
    {
        extent1= reports.startTest("Verify get test case");
        Response response= RestAssured.given().when().get("/posts/1");
        Assert.assertEquals(response.getStatusCode(),200);
        String value1 = response.path("title");
        System.out.println("value   "   +value1);
        Assert.assertEquals(response.path(key),value);
        System.out.println(response.prettyPrint());


        extent1.log(LogStatus.PASS,"getup successfully loaded");

    }
    @Test
    public void createPosts(String key , Object value){
        extent1= reports.startTest("Verify create test case");
        Response response = RestAssured.given().when().body("{{\"userId\":1},{\"title\":\"Pushparaj\"},{\"body\":\"MaiJhukeganahi\"}}").post("/posts");
        Assert.assertEquals(response.getStatusCode(),201);
        // Assert.assertEquals(response.path(key),value);
        System.out.println(response.prettyPrint());
        extent1.log(LogStatus.PASS,"created successfully");
    }

    @Test
    public void deleteposts(){
        extent1= reports.startTest("Verify delete test case");
        Response response = RestAssured.given().when().delete("/posts/1");
        Assert.assertEquals(response.getStatusCode(),200);
                System.out.println(response.prettyPrint());
        extent1.log(LogStatus.FAIL,"deleted successfully");

    }
    @Test
    public void createPut(){
   extent1= reports.startTest("Verify put test case");
        Response response = RestAssured.given().when().body("{{\"userId\":1},{\"title\":\"Pushparaj2\"},{\"body\":\"MaiJhukeganahi\"}}").put("/posts");
        Assert.assertEquals(response.getStatusCode(),201);
        // Assert.assertEquals(response.path(key),value);
        System.out.println(response.prettyPrint());
     extent1.log(LogStatus.PASS," successfully updated");
  }


@AfterClass
public void end(){
    reports.endTest(extent1);
    reports.flush();
    reports.close();
}

}
