package com.faculdade.epidem.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Classe respons???vel por conter as informa??????es de coordenadas
 * 
 * @author Guilherme Ramos
 * @version 1.0
 */
public final class Coordenada implements Parcelable {

	private int Id;
	private String Descricao;
	private float Latitude;
	private float Longitude;
	private int Tipo;
	private String TipoTitle;

	public Coordenada() {
		// TODO Auto-generated constructor stub
	}
	
	public Coordenada(Parcel in) {
		this.Id = in.readInt();
		this.Latitude = in.readFloat();
		this.Longitude = in.readFloat();
		this.Descricao = in.readString();
		this.Tipo = in.readInt();
		this.TipoTitle = in.readString();
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getDescricao() {
		return Descricao;
	}

	public void setDescricao(String descricao) {
		Descricao = descricao;
	}

	public float getLatitude() {
		return Latitude;
	}

	public void setLatitude(float latitute) {
		Latitude = latitute;
	}

	public float getLongitude() {
		return Longitude;
	}

	public void setLongitude(float longitude) {
		Longitude = longitude;
	}
	
	public int getTipo(){
		return Tipo;
	}
	
	public void setTipo(int Tipo){
		this.Tipo = Tipo;
	}
	
	public String getTipoTitle(){
		return TipoTitle;
	}
	
	public void setTipoTitle(String TipoTitle){
		this.TipoTitle = TipoTitle;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((Descricao == null) ? 0 : Descricao.hashCode());
		result = prime * result + Id;
		result = prime * result + Float.floatToIntBits(Latitude);
		result = prime * result + Float.floatToIntBits(Longitude);
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
		Coordenada other = (Coordenada) obj;
		if (Descricao == null) {
			if (other.Descricao != null)
				return false;
		} else if (!Descricao.equals(other.Descricao))
			return false;
		if (Id != other.Id)
			return false;
		if (Float.floatToIntBits(Latitude) != Float
				.floatToIntBits(other.Latitude))
			return false;
		if (Float.floatToIntBits(Longitude) != Float
				.floatToIntBits(other.Longitude))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "{ Coordenada :[{Id:" + Id + ", Descricao:" + Descricao
				+ ", Latitude:" + Latitude + ", Longitude:" + Longitude + "}] }";
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(Id);
		out.writeFloat(Latitude);
		out.writeFloat(Longitude);
		out.writeString(Descricao);
		out.writeInt(Tipo);
		out.writeString(TipoTitle);
	}

	/**
	 * Respons???vel por criar o parcel da classe
	 */
	public static final Creator<Coordenada> CREATOR
		= new Creator<Coordenada>() {
			@Override
			public Coordenada[] newArray(int size) {
				return new Coordenada[size];
			}
			
			@Override
			public Coordenada createFromParcel(Parcel in) {
				return new Coordenada(in);
			}
		};

}
