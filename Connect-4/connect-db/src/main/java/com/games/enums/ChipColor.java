package com.games.enums;

public enum ChipColor {

	RED((byte)1), BLUE((byte)2);
	
	private byte colorCode;
	
	ChipColor(byte colorCode) {
		this.colorCode = colorCode;
	}

	public byte getColorCode() {
		return colorCode;
	}	
	
	
}
