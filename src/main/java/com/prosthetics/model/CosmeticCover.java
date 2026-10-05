package com.prosthetics.model;

/** Represents the cosmetic (external appearance) cover of the prosthesis. */
public class CosmeticCover implements Cloneable {

    private String colorOrSkinTone;
    private String finish; // e.g. "Matte", "Glossy", "Realistic Skin Texture"

    public CosmeticCover(String colorOrSkinTone, String finish) {
        this.colorOrSkinTone = colorOrSkinTone;
        this.finish = finish;
    }

    public String getColorOrSkinTone() {
        return colorOrSkinTone;
    }

    public void setColorOrSkinTone(String colorOrSkinTone) {
        this.colorOrSkinTone = colorOrSkinTone;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    @Override
    public CosmeticCover clone() {
        try {
            return (CosmeticCover) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("CosmeticCover is Cloneable, this should never happen", e);
        }
    }

    @Override
    public String toString() {
        return String.format("CosmeticCover[color=%s, finish=%s]", colorOrSkinTone, finish);
    }
}
