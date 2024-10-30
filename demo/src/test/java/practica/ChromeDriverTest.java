package practica;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

@TestMethodOrder(OrderAnnotation.class)
public class ChromeDriverTest {
	WebDriver driver;
	
	@BeforeAll
	static void configuracionInicial() {
		//1. Configurar y descargar el driver
		WebDriverManager.chromedriver().setup();
	}
	
	@BeforeEach
	void instanciarDriver() {
		//2. instancia
		driver = new ChromeDriver();
	}
	
	@Order(1)
	@Test
	void abrirPaginaSeleniumTest() {
    
		driver.get("https://tornadobus.com/");

        WebDriverWait wait = new WebDriverWait(driver,25); // Esperar segundos
        // Espero hasta que el modal sea visible
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("elementor-popup-modal-6408")));

        // Encontrar el botón de cierre usando sus clases
        WebElement closeButton = modal.findElement(By.cssSelector("a.dialog-close-button.dialog-lightbox-close-button"));

        // Hacer clic en el botón de cierre
        closeButton.click();

        // Crear una espera explícita
        WebDriverWait wait2 = new WebDriverWait(driver, 20);

        WebElement searchField = wait2.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchOrigin:r2:")));

        searchField.click();

        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Wichita, (KS - Paqueteria Mexico LLC)')]")));

        option.click();


        WebDriverWait wait3 = new WebDriverWait(driver, 10);
        WebElement searchField2 = wait3.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchDestiny:r2:")));

        searchField2.click();
        WebElement option2 = wait3.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Maravatio de Ocampo, (MICH - Maravatio-Maravatio de Ocampo)')]")));

        option2.click();
        WebDriverWait wait4 = new WebDriverWait(driver, 10);


        WebElement passengersField = wait4.until(ExpectedConditions.visibilityOfElementLocated(By.id("numericInput")));
        passengersField.click();
        
        WebElement searchButton = wait4.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='button-main-search-travel-full button-main-search-travel-active']")));
        searchButton.click();

        }
		
	}