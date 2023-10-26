package tnrWebUI

import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc
import org.openqa.selenium.By
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

public class ScreenshotManager {

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
	}


	/**
	 * Prend une capture d'écran et ajoute un cadre autour de l'élément spécifié.
	 *
	 * @param driver Le web driver 
	 * @param by L'identifiant de l'élément à encadrer.
	 * @param outputPath Le chemin où enregistrer la capture d'écran.
	 */
	static void takeScreenshot(WebDriver driver, By by, String outputPath) {
		WebElement element = driver.findElement(by)

		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)

		Mat src = Imgcodecs.imread(screenshot.getAbsolutePath())

		Point point1 = new Point(element.getLocation().x, element.getLocation().y)
		Point point2 = new Point(element.getLocation().x + element.getSize().getWidth(), element.getLocation().y + element.getSize().getHeight())

		Imgproc.rectangle(src, point1, point2, new Scalar(0, 255, 0), 2)

		Imgcodecs.imwrite(outputPath, src)
	}
}

