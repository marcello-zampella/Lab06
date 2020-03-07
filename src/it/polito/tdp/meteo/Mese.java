package it.polito.tdp.meteo;

public class Mese {
	String mese;
	int numero;
	public Mese(String mese, int numero) {
		super();
		this.mese = mese;
		this.numero = numero;
	}
	public String getMese() {
		return mese;
	}
	public void setMese(String mese) {
		this.mese = mese;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	@Override
	public String toString() {
		return mese;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mese == null) ? 0 : mese.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mese other = (Mese) obj;
		if (mese == null) {
			if (other.mese != null)
				return false;
		} else if (!mese.equals(other.mese))
			return false;
		return true;
	}
	
	
	
	
}
