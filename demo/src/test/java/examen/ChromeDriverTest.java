package examen;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

@TestMethodOrder(OrderAnnotation.class)
public class ChromeDriverTest {

    WebDriver driver;
    static ExtentReports extent;
    ExtentTest test;

    @BeforeAll
    static void configuracionInicial() {
        WebDriverManager.chromedriver().setup();
        
        // Configurar ExtentReports
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("ReporteDePruebas.html");
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle("Reporte de Automatización");
        htmlReporter.config().setReportName("Pruebas de ChromeDriver");

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        System.out.println("Reporte generado exitosamente.");
    }

    @BeforeEach
    void instanciarDriver() {
        driver = new ChromeDriver();
        test = extent.createTest("Prueba en TornadoBus");
    }

    // Test 1: Abrir página y realizar búsqueda
    @Order(1)
    @Test
    void abrirPaginaSeleniumTest() {
        test.log(Status.INFO, "Abriendo la página de TornadoBus");
        
        try {
            driver.get("https://tornadobus.com/");

            WebDriverWait wait = new WebDriverWait(driver, 25);
            
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("elementor-popup-modal-6408")));
            WebElement closeButton = modal.findElement(By.cssSelector("a.dialog-close-button.dialog-lightbox-close-button"));
            closeButton.click();
            test.log(Status.PASS, "Modal cerrado correctamente");

            WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchOrigin:r2:")));
            searchField.click();

            WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Kansas City, KS, (KS - TBC Kansas City)')]")));
            option.click();
            searchField.sendKeys("Kansas City, KS"); // Ingresar el texto deseado
            searchField.sendKeys(Keys.DOWN);
            test.log(Status.PASS, "Campo de origen completado exitosamente con la opción 'Kansas City, KS, (KS - TBC Kansas City)'.");

            WebElement searchField2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchDestiny:r2:")));
            searchField2.click();

            WebElement option2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'San Lucas, (MICH - Abarrotes-San Lucas)')]")));
            option2.click();
            searchField2.sendKeys("San Lucas"); // Ingresar el texto deseado
            searchField2.sendKeys(Keys.DOWN); 
            test.log(Status.PASS, "Campo de destino completado exitosamente con la opción 'San Lucas, (MICH - Abarrotes-San Lucas)'.");

            WebElement passengersField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("numericInput")));
            passengersField.click();

            WebElement searchButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='button-main-search-travel-full button-main-search-travel-active']")));
            searchButton.click();
            test.log(Status.PASS, "Búsqueda realizada con éxito");
        } catch (Exception e) {
            test.log(Status.FAIL, "Error en la prueba abrirPaginaSeleniumTest: " + e.getMessage());
        }
    }

    // Test 2: Completar formulario de registro
    @Order(2)
    @Test
    void formularioRegistroTest() {
        test.log(Status.INFO, "Abriendo la página de registro de TornadoBus");

        try {
            driver.get("https://webtec.tornadobus.com/register?_gl=1*1dttz29*_gcl_au*NDgzOTE4MDEyLjE3MjkwOTM2Njk.*_ga*NzM0NjAxNTIzLjE3MjkwOTM2Njk.*_ga_BYHG1P6TGT*MTczMDMwNDU3NC4xMS4wLjE3MzAzMDQ1NzQuMC4wLjA.");
            
            WebDriverWait wait = new WebDriverWait(driver, 40);
            
            // Completar campos de registro con datos
            WebElement nombre = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(":r1:")));
            nombre.sendKeys("Fidel");

            WebElement apellido= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(":r2:")));
            apellido.sendKeys("sante");

            ///
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(":rm:")));

            // Ingresa el nombre de usuario
            usernameField.sendKeys("fsante");
            System.out.println("Nombre de usuario ingresado exitosamente.");

            WebElement genderInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gender")));
            genderInput.click();
            genderInput.sendKeys("Masculino"); 
            test.log(Status.PASS, "Género seleccionado exitosamente.");

            WebElement email = driver.findElement(By.id(":r5:"));
            email.sendKeys("fidel.sante@example.com");

            WebElement password = driver.findElement(By.id(":r8"));
            password.sendKeys("Password123!");

            WebElement confirmPassword = driver.findElement(By.id(":r9"));
            confirmPassword.sendKeys("Password123!");

            WebElement termsCheckbox = driver.findElement(By.id("termsCheckbox")); 
            if (!termsCheckbox.isSelected()) {
                termsCheckbox.click();
            }

            WebElement submitButton = driver.findElement(By.id("registerButton")); 
            submitButton.click();

            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessage"))); 
            if (successMessage.isDisplayed()) {
                test.log(Status.PASS, "Registro completado exitosamente.");
            } else {
                test.log(Status.FAIL, "El registro no se pudo completar.");
            }

            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='button' and contains(., 'Iniciar sesion')]")));
            loginButton.click();
            test.log(Status.PASS, "Botón 'Iniciar sesión' clicado exitosamente.");

       } catch (Exception e) {
            test.log(Status.FAIL, "Error en la prueba formularioRegistroTest: " + e.getMessage());
        }
    }

    @AfterEach
    void cerrarDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterAll
    static void finalizarReporte() {
        extent.flush();
    }
}
