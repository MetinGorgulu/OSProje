package Cizelgeleyici;
//Bismillah
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
public class ProsesYonetici {
	public int varisZamani;
	public int oncelik;
	public int patlamaZamani;
	
	public int id;
	public int durum;	//Başlangıç mı yoksa yürütülyor mu kontrol değişkeni.
	
	public Process process;
	public String renk;
	public String[] renkler = new String[] {"\u001B[31m", "\u001B[32m", "\u001B[33m","\u001B[34m","\u001B[35m","\u001B[36m","\u001B[38m","\u001B[39m"}; ;
	public ProsesYonetici() {
		int randomNum = ThreadLocalRandom.current().nextInt(0, 8);
		
		renk=renkler[randomNum];
		durum=0;
	}
	
	public void ProsesBaslatildi(int saniye , ProsesYonetici p) throws IOException {
		List<String> params = java.util.Arrays.asList( "java", "-jar","Process.jar",Integer.toString(saniye),Integer.toString(p.id),
		Integer.toString(p.oncelik),Integer.toString(p.patlamaZamani),"basladi",p.renk);
		ProcessBuilder builder = new ProcessBuilder(params);
		process=builder.start();		
		Scanner scanner = new Scanner(process.getInputStream());
		while (scanner.hasNextLine()) {
		    System.out.println(scanner.nextLine());
		}
		
	}
	public void ProsesYurutuluyor(int saniye , ProsesYonetici p) throws IOException {
		
		List<String> params = java.util.Arrays.asList( "java", "-jar","Process.jar",Integer.toString(saniye),Integer.toString(p.id),
				Integer.toString(p.oncelik),Integer.toString(p.patlamaZamani),"yurutuluyor",p.renk);
				ProcessBuilder builder = new ProcessBuilder(params);
				process=builder.start();

				Scanner scanner = new Scanner(process.getInputStream());
				while (scanner.hasNextLine()) {
				    System.out.println(scanner.nextLine());
				}
		
	}
	public void ProsesAskida(int saniye , ProsesYonetici p, int kontrolId,int kontrolOncelik,int kontrolPatlama,int kontrolVaris,String kontrolRenk) throws IOException, InterruptedException {
		List<String> params = java.util.Arrays.asList( "java", "-jar","Process.jar",Integer.toString(saniye),Integer.toString(kontrolId),
				Integer.toString(kontrolOncelik),Integer.toString(kontrolPatlama),"askida",kontrolRenk);
				ProcessBuilder builder = new ProcessBuilder(params);
				process=builder.start();
				Scanner scanner = new Scanner(process.getInputStream());
				while (scanner.hasNextLine()) {
				    System.out.println(scanner.nextLine());
				}
				process.waitFor();
		
	}
	public void ProsesSonlandi(int saniye , ProsesYonetici p) throws IOException {
		
		List<String> params = java.util.Arrays.asList( "java", "-jar","Process.jar",Integer.toString(saniye),Integer.toString(p.id),
				Integer.toString(p.oncelik),Integer.toString(p.patlamaZamani),"sonlandi",p.renk);
				ProcessBuilder builder = new ProcessBuilder(params);
				process=builder.start();
				Scanner scanner = new Scanner(process.getInputStream());
				while (scanner.hasNextLine()) {
				    System.out.println(scanner.nextLine());
				}
				process.destroy();	
	}
	
	
public void ProsesZamanAsimi(int saniye , ProsesYonetici p) throws IOException {
		List<String> params = java.util.Arrays.asList( "java", "-jar","Process.jar",Integer.toString(saniye),Integer.toString(p.id),
				Integer.toString(p.oncelik),Integer.toString(p.patlamaZamani),"zamanAsimi",p.renk);
				ProcessBuilder builder = new ProcessBuilder(params);
				process=builder.start();
				Scanner scanner = new Scanner(process.getInputStream());
				while (scanner.hasNextLine()) {
				    System.out.println(scanner.nextLine());
				}
			
		
	}
}
