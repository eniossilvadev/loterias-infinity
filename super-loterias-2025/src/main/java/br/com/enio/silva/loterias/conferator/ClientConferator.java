package br.com.enio.silva.loterias.conferator;

public class ClientConferator {

	public static void main(String[] args) {

		//		Conferator cms = new ConferatorMegaSena();
		//		cms.confere(2579);

		//		Conferator clf = new ConferatorLotofacil();
		//		clf.confere(2726);

		int concursoDupla = 2499;

		Conferator clf1 = new ConferatorDuplaSena1();
		clf1.confere(concursoDupla, true);

		Conferator clf2 = new ConferatorDuplaSena2();
		clf2.confere(concursoDupla, true);
	}

}
