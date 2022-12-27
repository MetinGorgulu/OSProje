package Cizelgeleyici;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class Gorevlendirici {
	public Queue realTime = new LinkedList<ProsesYonetici>();	//Gerçek zamanlı proses kuyruğu
	
	//Kullanıcı proses kuyruğu
	public Queue user1 = new LinkedList<ProsesYonetici>();
	public Queue user2 = new LinkedList<ProsesYonetici>();
	public Queue user3 = new LinkedList<ProsesYonetici>();
	
	static int saniye;
	static int kontrolId = -1;
	static int kontrolVaris = -1;
	static int kontrolOncelik = -1;
	static int kontrolPatlama = -1;
	static String kontrolRenk = "";
	
	//Proses önceliğe göre kuyruklara eklenir.
	public void ekle(ProsesYonetici p) {
		if(p.oncelik == 0) {
			realTime.add(p);
		}
		else if(p.oncelik == 1) {
			user1.add(p);
		}
		else if(p.oncelik == 2) {
			user2.add(p);
		}
		else {
			user3.add(p);
		}
	}
	
	public void calistir(ProsesYonetici p) throws IOException {
		ProsesListesi plist=new ProsesListesi();
		Gorevlendirici gorevlendirici = new Gorevlendirici();
		Timer myTimer = new Timer();
		saniye=-1;
		
		TimerTask gorev = new TimerTask() {	//Zamanlayıcı başladı.
			//Her saniye tekrar eden döngü.
			@Override
			public void run() {
				for	(int i=0;i<plist.prosesler.size();i++) {	//Proses listesini gezen döngü.
					if(plist.prosesler.get(i).varisZamani == saniye) {	//Zamanı gelen proses görevlendirici tarafından kuyruklara atılır.
						gorevlendirici.ekle(plist.prosesler.get(i));	//kuyruğa ekleme.
						plist.prosesler.remove(i--);	//eklenen prosesi listeden silme.
					}
				}
				

				try {
					gorevlendirici.yazdir(gorevlendirici, p);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
				if(gorevlendirici.realTime.size() == 0 && gorevlendirici.user1.size() == 0 && gorevlendirici.user2.size() == 0 && gorevlendirici.user3.size() == 0 && plist.prosesler.size() == 0) 
				{
					System.out.print("Görevlendirici Sonlandı...");
					myTimer.cancel();
				}
				
			}
		};
		
		myTimer.schedule(gorev,0,100);
	}
	
	public void yazdir(Gorevlendirici gorevlendirici, ProsesYonetici p) throws IOException, InterruptedException {
		
		

		for(int i=0;i<realTime.size();i++) {
			p = (ProsesYonetici) gorevlendirici.realTime.poll();
			if(saniye - p.varisZamani>20) {	//20 saniyeyi geçen prosesler sonlanır.
				try {
					p.ProsesZamanAsimi(saniye, p);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i--;	
			}
			else gorevlendirici.realTime.add(p);
		}

		for(int i=0;i<user1.size();i++) {
			p = (ProsesYonetici) gorevlendirici.user1.poll();
			if(saniye - p.varisZamani>20) {	//20 saniyeyi geçen prosesler sonlanır.
				try {
					p.ProsesZamanAsimi(saniye, p);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i--;
			}
			else user1.add(p);
		}

		for(int i=0;i<user2.size();i++) {
			p = (ProsesYonetici) gorevlendirici.user2.poll();
			if(saniye - p.varisZamani>20) {	//20 saniyeyi geçen prosesler sonlanır.
				try {
					p.ProsesZamanAsimi(saniye, p);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i--;
			}
			else gorevlendirici.user2.add(p);
		}

		for(int i=0;i<user3.size();i++) {
			p = (ProsesYonetici) gorevlendirici.user3.poll();
			if(saniye - p.varisZamani>20) {	//20 saniyeyi geçen prosesler sonlanır.
				try {
					p.ProsesZamanAsimi(saniye, p);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i--;
			}
			else gorevlendirici.user3.add(p);
		}
		
		
		
		
		
		
		
		
		if(gorevlendirici.realTime.size() != 0) {	//Gerçek zamanlı kuyruğunda proses varsa işleme alınır.(FCFS)
			p = (ProsesYonetici) gorevlendirici.realTime.peek();
			
		
				if(p.id != kontrolId && kontrolId != -1) {	//Askıya alınan proses ekran çıktısı.
					p.ProsesAskida(saniye, p, kontrolId, kontrolOncelik, kontrolPatlama, kontrolVaris,kontrolRenk);
					kontrolId=-1;
				}
				if(p.durum == 0) {
					p.ProsesBaslatildi(saniye, p);
					p.patlamaZamani--;
					p.durum++;
				}
				else if(p.patlamaZamani != 0) {
					p.ProsesYurutuluyor(saniye, p);
					p.patlamaZamani--;
				}
				if(p.patlamaZamani==0) {
					try {	//prosesin bitmesi beklenir.
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					
					p.ProsesSonlandi(saniye+1, p);
					gorevlendirici.realTime.remove();
					kontrolId=-1;	//Proses sonlandığından kontrol etmeye gerek yok.
				}
			
			
			
		}
		
		else if(gorevlendirici.user1.size() != 0) {	//Kullanıcı Proses kuyruğunun ilk kuyruğu
			p = (ProsesYonetici) gorevlendirici.user1.poll();	//Proses kuyruktan alındı.
			
			
				if(p.id != kontrolId && kontrolId != -1) {
					p.ProsesAskida(saniye, p, kontrolId, kontrolOncelik, kontrolPatlama, kontrolVaris,kontrolRenk);
					kontrolId=-1;
				}
				
				if(p.durum == 0) {	//Proses Başlatıldı
					p.ProsesBaslatildi(saniye, p);
					p.oncelik++;
					p.patlamaZamani--;
					p.durum++;
				}
				else if(p.patlamaZamani != 0) {	//Proses yürütülüyor.
					p.ProsesYurutuluyor(saniye, p);
					p.oncelik++;
					p.patlamaZamani--;
				}
				if(p.patlamaZamani==0) {	//Proses, son saniyesindeyse;
					//proses bitene kadar beklenir bittikten sonra silinir.
					try {	//prosesin bitmesi beklenir.
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					p.ProsesSonlandi(saniye+1, p);
					kontrolId = -1;
				}
				else {
					gorevlendirici.user2.add(p);	//Proses bitmemişse bir alt kuyruğa atılır.
					
					//Proses Askıya alındı mı kontrol için bilgileri yedeklenir.
					kontrolId = p.id;
					kontrolOncelik=p.oncelik;
					kontrolVaris=p.varisZamani;
					kontrolPatlama=p.patlamaZamani;
					kontrolRenk=p.renk;
				}
			
			
			
		}
		
		else if(gorevlendirici.user2.size() != 0) {	//Kullanıcı Proses kuyruğunun ikinci kuyruğu
			p = (ProsesYonetici) gorevlendirici.user2.poll();
			

				if(p.id != kontrolId && kontrolId != -1) {
					p.ProsesAskida(saniye, p, kontrolId, kontrolOncelik, kontrolPatlama, kontrolVaris,kontrolRenk);
					kontrolId=-1;
				}
				
				if(p.durum == 0) {	//Proses Başlatıldı
					p.ProsesBaslatildi(saniye, p);
					p.oncelik++;
					p.patlamaZamani--;
					p.durum++;
				}
				else if(p.patlamaZamani != 0) {
					p.ProsesYurutuluyor(saniye, p);
					p.oncelik++;
					p.patlamaZamani--;
				}
				if(p.patlamaZamani==0) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					p.ProsesSonlandi(saniye+1, p);
					kontrolId = -1;
				}
				else {
					gorevlendirici.user3.add(p);	
					kontrolId = p.id;
					kontrolOncelik=p.oncelik;
					kontrolVaris=p.varisZamani;
					kontrolPatlama=p.patlamaZamani;
					kontrolRenk=p.renk;
				}
			
			
				
		}
		
		else if(gorevlendirici.user3.size() != 0) {	//Kullanıcı Proses kuyruğunun üçüncü kuyruğu
			
			p = (ProsesYonetici) gorevlendirici.user3.poll();

			
				if(p.id != kontrolId && kontrolId != -1) {
					p.ProsesAskida(saniye, p, kontrolId, kontrolOncelik, kontrolPatlama, kontrolVaris,kontrolRenk);
					kontrolId=-1;
				}
				
				if(p.durum == 0) {	//Proses Başlatıldı
					p.ProsesBaslatildi(saniye, p);
					p.patlamaZamani--;
					p.durum++;
				}
				else if(p.patlamaZamani != 0) {	//Proses çalıştıktan sonra tekrar kuyruğun sonuna atılır.
					p.ProsesYurutuluyor(saniye, p);
					p.patlamaZamani--;
				}
				
				
				if(p.patlamaZamani==0) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					p.ProsesSonlandi(saniye+1, p);
					kontrolId = -1;
				}
				else {
					gorevlendirici.user3.add(p);
					kontrolId = p.id;
					kontrolOncelik=p.oncelik;
					kontrolVaris=p.varisZamani;
					kontrolPatlama=p.patlamaZamani;
					kontrolRenk=p.renk;
				}
				
			
			
			
		}
		saniye++;
	}
	
}
