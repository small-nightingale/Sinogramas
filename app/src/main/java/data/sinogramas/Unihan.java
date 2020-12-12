package data.sinogramas;

import java.util.Arrays;
import java.util.Comparator;

/**
 * This class represents a character from the Unihan database
 * @author Cristian Davil Camilo Santos Gil
 * @author Diego Esteban Quintero Rey
 * @author Kevin Jair Gonzalez Sanchez
 * @author Stiven Leonardo Sánchez León
 * @version 2.0
 * @since 15/11/2020
 */

public class Unihan implements Comparable<Unihan>{

    private boolean isBMP;
    private char character;
    private char highSurrogative;
    private char lowSurrogative;

    private double score; // Campo agregado para ordenar resultados en el MaxHeap al hacer searchPattern

    private int numOfStrokes;
    private String characterInString;
    private String codePoint;
    private String mp3file;
    private String pinyin;
    private String radix;
    private String[] englishDefinitions;
    private String[] pictureLinks;
    private String[] spanishDefinitions;
    
    // Constructor agregado para el MaxHeap específicamente
    // Permite crear un Unihan de mentiras con score = MAX_VALUE
    public Unihan(double s) {
        this.score = s;
    }
    
    public Unihan(int numOfStrokes, String codePoint, String mp3file, String pinyin, String radix, String[] englishDefinitions, String[] pictureLinks, String[] spanishDefinitions) {
        this.numOfStrokes = numOfStrokes;
        this.codePoint = codePoint;
        this.mp3file = mp3file;
        this.pinyin = pinyin;
        this.radix = radix;
        this.englishDefinitions = englishDefinitions;
        this.pictureLinks = pictureLinks;
        this.spanishDefinitions = spanishDefinitions;

        this.character = stringToChar(codePoint);
        setBMP(this.codePoint);

        if (isBMP()) {
            setHighSurrogative(codePoint);
            setLowSurrogative(codePoint);
            this.characterInString = String.valueOf(getHighSurrogative()) + String.valueOf(getLowSurrogative());
        } else {
            setHighSurrogative(Character.MIN_VALUE);
            setLowSurrogative(Character.MAX_VALUE);
            this.characterInString = String.valueOf(codePoint);
        }
    }
    public Unihan (String codePoint) {
        this(-1,codePoint,null,null,null,null,null,null);
    }
    public Unihan(String chrStr, String radix, String meaning, String pinyin, String strokes) {
        this(Integer.valueOf(strokes),strToCodePoint(chrStr),null,pinyin,radix,null,null,meaning.split(" "));
    }

    public boolean isBMP() {
        return this.isBMP;
    }
    public void setBMP(boolean BMP) {
        this.isBMP = BMP;
    }
    public void setBMP(String codePoint) {
        if (codePoint.length()>6) this.isBMP = true;
        else this.isBMP = false;
    }
    public char getCharacter() {
        return this.character;
    }
    public void setCharacter(char character) {
        this.character = character;
    }
    public char getHighSurrogative() {
        return this.highSurrogative;
    }
    public void setHighSurrogative(char highSurrogative) {
        this.highSurrogative = highSurrogative;
    }
    public void setHighSurrogative(String codePoint) {
        String diff = Unihan.hexSub(codePoint,"10000");
        String div = Unihan.hexDiv(diff,"400");
        String sum = Unihan.hexSum(div,"D800");
        this.highSurrogative = Unihan.stringToChar(sum);
    }
    public char getLowSurrogative() {
        return this.lowSurrogative;
    }
    public void setLowSurrogative(char lowSurrogative) {
        this.lowSurrogative = lowSurrogative;
    }
    public void setLowSurrogative(String codePoint) {
        String diff = Unihan.hexSub(codePoint,"10000");
        String mod = Unihan.hexDiv(diff,"400");
        String sum = Unihan.hexSum(mod,"DC00");
        this.lowSurrogative = Unihan.stringToChar(sum);
    }
    public int getNumOfStrokes() {
        return this.numOfStrokes;
    }
    public void setNumOfStrokes(int numOfStrokes) {
        this.numOfStrokes = numOfStrokes;
    }
    public String getCharacterInString() {
        return this.characterInString;
    }
    public void setCharacterInString(String characterInString) {
        this.characterInString = characterInString;
    }
    public String getCodePoint() {
        return this.codePoint;
    }
    public void setCodePoint(String codePoint) {
        this.codePoint = codePoint;
    }
    public String getMp3file() {
        return this.mp3file;
    }
    public void setMp3file(String mp3file) {
        this.mp3file = mp3file;
    }
    public String getPinyin() {
        return this.pinyin;
    }
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    public String getRadix() {
        return this.radix;
    }
    public void setRadix(String radix) {
        this.radix = radix;
    }
    public String[] getEnglishDefinitions() {
        return this.englishDefinitions;
    }
    public void setEnglishDefinitions(String[] englishDefinitions) {
        this.englishDefinitions = englishDefinitions;
    }
    public String[] getPictureLinks() {
        return this.pictureLinks;
    }
    public void setPictureLinks(String[] pictureLinks) {
        this.pictureLinks = pictureLinks;
    }
    public String[] getSpanishDefinitions() {
        return this.spanishDefinitions;
    }
    public void setSpanishDefinitions(String[] spanishDefinitions) {
        this.spanishDefinitions = spanishDefinitions;
    }
    public String getFirstSpanishDefinition() {
        return this.spanishDefinitions[0];
    }
    public void setScore(double s) {
        this.score = s;
    }
    public double getScore() {
        return this.score;
    }
    public String getSinogram() {
        return String.valueOf(stringToChar(this.codePoint));
    }

    public static char stringToChar(String codePoint) {
        char toReturn = ' ';
        if (codePoint.startsWith("U")) toReturn = Character.toChars(Integer.parseInt(codePoint.substring(2),16))[0];
        else toReturn = Character.toChars(Integer.parseInt(codePoint,16))[0];
        return  toReturn;
    }
    public static int hexToDec(String hex) {
        return Integer.parseInt(hex,16);
    }
    public static String decToHex(int dec) {
        return  Integer.toHexString(dec);
    }
    public static String strToCodePoint(String chrStr) {
        return String.valueOf(decToHex(chrStr.codePointAt(0)));
    }

    public static String hexSum(String item1, String item2) {
        int num1 = Unihan.hexToDec(item1);
        int num2 = Unihan.hexToDec(item2);
        int result = num1 + num2;
        return Unihan.decToHex(result);
    }
    public static String hexSub(String item1, String item2) {
        int num1 = Unihan.hexToDec(item1);
        int num2 = Unihan.hexToDec(item2);
        int result = num1 - num2;
        return Unihan.decToHex(result);
    }
    public static String hexProd(String item1, String item2) {
        int num1 = Unihan.hexToDec(item1);
        int num2 = Unihan.hexToDec(item2);
        int result = num1 * num2;
        return Unihan.decToHex(result);
    }
    public static String hexDiv(String item1, String item2) {
        int num1 = Unihan.hexToDec(item1);
        int num2 = Unihan.hexToDec(item2);
        int result;
        if (num2!=0) result = num1 / num2;
        else result = 0;
        return Unihan.decToHex(result);
    }
    public static String hexMod(String item1, String item2) {
        int num1 = Unihan.hexToDec(item1);
        int num2 = Unihan.hexToDec(item2);
        int result;
        if (num2!=0) result = num1 % num2;
        else result = 0;
        return Unihan.decToHex(result);
    }

    public String print() {
        String englishDefinitionsToString="[";
        String pictureLinksToString="[";
        String spanishDefinitionsToString="[";

        for(String english: englishDefinitions) englishDefinitionsToString+=english+",";
        englishDefinitionsToString+="]";

        for(String spanish: spanishDefinitions) spanishDefinitionsToString+=spanish;
        spanishDefinitionsToString+="]";

        for(String pictures: pictureLinks) pictureLinksToString+=pictures;
        pictureLinksToString+="]";

        return numOfStrokes+":"+codePoint+":"+mp3file+":"+pinyin+":"+radix+":"+englishDefinitionsToString+":"+pictureLinksToString+":"+spanishDefinitionsToString;
    }

    @Override
    public String toString() {
        return String.valueOf(this.character);
    }
    @Override
    public int compareTo(Unihan otherCharacter) {
        int toReturn;
        if (this.character>otherCharacter.getCharacter()) {
            toReturn = 1;
        }
        else if (this.character==otherCharacter.getCharacter()) {
            toReturn = 0;
        }
        else {
            toReturn = -1;
        }
        return toReturn;
    }
}
