package Cizelgeleyici;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Iterator;


public class ProsesListesi {

public List<ProsesYonetici> prosesler = new ArrayList<ProsesYonetici>();
	
	
	//Proses listesi oluşurken dosyadan okuma işlemi yapıp bu bilgiler eşliğinde prosesleri oluşturup
	//Proses listesine atar.
public ProsesListesi() throws IOException{
	int id = 0;
	File file =new File("giriş.txt");	//Dosya oluşturma
	
	FileReader freader = new FileReader(file);	//Dosyayı okuma
	String line;
	BufferedReader bReader = new BufferedReader(freader);
	
	while ((line = bReader.readLine())!= null) {	//Tüm satırları okuma
		String[] dizi= line.split(", ");	//Vırgül ve boşlukların dışındaki karakterler çekildi
		
		for (int i=0;i<dizi.length;i++) {
			ProsesYonetici p = new ProsesYonetici();
			p.id = id++;	//Proseslere 0'dan başlayıp id ataması yapar.
			p.varisZamani = Integer.parseInt(dizi[i]);
			p.oncelik = Integer.parseInt(dizi[++i]);
			p.patlamaZamani = Integer.parseInt(dizi[++i]);
			
			prosesler.add(p);
			
		}
		
		
	}
	bReader.close();
	
}
	
}
