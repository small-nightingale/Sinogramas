/**
 * Undocumented methods are getters and setters
 */
package logic.sinogramas;
import java.util.LinkedList;
/**
 * This class is meant to manage the data within the data structures.
 * @author dequi
 * @author small-nightingale
 * @version 8.0
 * @since 16/10/2020
 */

import java.util.ArrayList;
import java.util.NoSuchElementException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import data.sinogramas.*;
import static java.lang.Float.parseFloat;

public class Archive {

    public BufferedReader text;  //Representation of the text in memory
    private InputStream textToParse;
    private int intRadix;

    // Llevan registro de cuales árboles se han creado en los arreglos de BSTs
    private int[] strokesList;
    private int[] favStrokesList;
    private int[] radixesList;
    private int[] favRadixesList;
    
    private AVLTreeGeneric<Unihan> tempAVL; // AVL principal o general
    
    //Arreglos de árboles BST que aplican en los filtros
    private BSTRefGeneric<Unihan>[] bstStrokesArray;
    private BSTRefGeneric<Unihan>[] favBSTStrokesArray;
    private BSTRefGeneric<Unihan>[] bstRadixesArray;
    private BSTRefGeneric<Unihan>[] favBSTRadixesArray;
    
    // Árbol auxiliar usado para instanciar los BST que se almacenan en los anteriores arreglos
    private BSTRefGeneric<Unihan> tempBST;
    
    private AVLTreeGeneric<Unihan> favAVL; // AVL de favoritos
    private ArrayList<SpanishDef> esDefArray; // Arreglo que guarda los SpanishDef
    private RabinKarp rabinKarp;
    
    /**
     * Class constructor, it initializates all the attributes within the class
     */
    public Archive() {
        this.strokesList = new int[59];
        this.favStrokesList = new int[59];
        this.radixesList = new int[215];
        this.favRadixesList = new int[215];
        this.bstRadixesArray = new BSTRefGeneric[215];
        this.favBSTRadixesArray = new BSTRefGeneric[215];
        this.bstStrokesArray = new BSTRefGeneric[59];
        this.favBSTStrokesArray = new BSTRefGeneric[59];
        this.tempAVL = new AVLTreeGeneric<>();
        this.favAVL = new AVLTreeGeneric<>();
        this.esDefArray = new ArrayList<>();
    }

    public BufferedReader getText() {
        return text;
    }
    public void setText(BufferedReader text) {
        this.text = text;
    }
    public int getIntRadix() {
        return intRadix;
    }
    public void setIntRadix(int intRadix) {
        this.intRadix = intRadix;
    }
    public int[] getStrokesList() {
        return strokesList;
    }
    public void setStrokesList(int[] strokesList) {
        this.strokesList = strokesList;
    }
    public int[] getFavStrokesList() {
        return favStrokesList;
    }
    public void setFavStrokesList(int[] favStrokesList) {
        this.favStrokesList = favStrokesList;
    }
    public int[] getRadixesList() {
        return radixesList;
    }
    public void setRadixesList(int[] radixesList) {
        this.radixesList = radixesList;
    }
    public int[] getFavRadixesList() {
        return favRadixesList;
    }
    public void setFavRadixesList(int[] favRadixesList) {
        this.favRadixesList = favRadixesList;
    }
    public AVLTreeGeneric<Unihan> getTempAVL() {
        return tempAVL;
    }
    public void setTempAVL(AVLTreeGeneric<Unihan> tempAVL) {
        this.tempAVL = tempAVL;
    }
    public BSTRefGeneric<Unihan>[] getBstStrokesArray() {
        return bstStrokesArray;
    }
    public void setBstStrokesArray(BSTRefGeneric<Unihan>[] bstStrokesArray) {
        this.bstStrokesArray = bstStrokesArray;
    }
    public BSTRefGeneric<Unihan>[] getFavBSTStrokesArray() {
        return favBSTStrokesArray;
    }
    public void setFavBSTStrokesArray(BSTRefGeneric<Unihan>[] favBSTStrokesArray) {
        this.favBSTStrokesArray = favBSTStrokesArray;
    }
    public BSTRefGeneric<Unihan>[] getBstRadixesArray() {
        return bstRadixesArray;
    }
    public void setBstRadixesArray(BSTRefGeneric<Unihan>[] bstRadixesArray) {
        this.bstRadixesArray = bstRadixesArray;
    }
    public BSTRefGeneric<Unihan>[] getFavBSTRadixesArray() {
        return favBSTRadixesArray;
    }
    public void setFavBSTRadixesArray(BSTRefGeneric<Unihan>[] favBSTRadixesArray) {
        this.favBSTRadixesArray = favBSTRadixesArray;
    }
    public BSTRefGeneric<Unihan> getTempBST() {
        return tempBST;
    }
    public void setTempBST(BSTRefGeneric<Unihan> tempBST) {
        this.tempBST = tempBST;
    }
    public AVLTreeGeneric<Unihan> getFavAVL() {
        return favAVL;
    }
    public void setFavAVL(AVLTreeGeneric<Unihan> favAVL) {
        this.favAVL = favAVL;
    }
    public ArrayList<SpanishDef> getEsDefArray() {
        return esDefArray;
    }
    public void setEsDefArray(ArrayList<SpanishDef> esDefArray) {
        this.esDefArray = esDefArray;
    }
    public RabinKarp getRabinKarp() {
        return rabinKarp;
    }
    public void setRabinKarp(RabinKarp rabinKarp) {
        this.rabinKarp = rabinKarp;
    }

    /**
     * This method loads a file into memory so it can be used
     */
    public void openFile(){
        this.text = new BufferedReader(new InputStreamReader(this.textToParse));
    }
    public void openFile(InputStream text){
        this.text = new BufferedReader(new InputStreamReader(text));
    }
    /**
     * This method de-loads a file off memory
     */
    public void closeFile() {
        try {
            this.text.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method reads a line: parse a complete line and skips over the next
     * @return read line - null if it is the end of the file - "?" if there is an exception
     */
    public String readLine() {
        String toReturn = "";
        try {
            toReturn = text.readLine();
        } catch (IOException e) {
            toReturn = "?";
        } catch (NoSuchElementException noSuchElementException) {
            toReturn = null;
        } finally {
            return toReturn;
        }
    }
    
    /**
     * This method takes 9 lines from the database and parse them, then creates an Unihan object
     * @return Unihan character with the information parsed
     */
    public Unihan parseChunck() {
        String chrStr = readLine();
        try {
            String[] englishDefinitions = stringToArray(readLine());
            String[] spanishDefinitions = stringToArray(readLine());
            String[] pictureLinks = stringToArray(readLine());
            String codePoint = readLine();
            int numOfStrokes = 0;
            String line = readLine();
            try {
                numOfStrokes = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                String[] s = line.split(" ");
                numOfStrokes = Integer.parseInt(s[0]);
            }
            String pinyin = readLine();
            String radix = readLine();
            String mp3file = readLine();
            Unihan thisChar = new Unihan(numOfStrokes,codePoint,mp3file,pinyin,radix,englishDefinitions,pictureLinks,spanishDefinitions);
            return thisChar;
        } catch (NoSuchElementException noSuchElementException) {
            Unihan emptyChar = new Unihan("U+0000");
            return emptyChar;
        } catch (NullPointerException nullPointerException) {
            Unihan emptyChar = new Unihan("U+0000");
            return emptyChar;
        }
    }

    // Funciona correctamente siempre que se lean los arcchivos individualmente (no el mergedFile)
    // No se deben leer las páginas 163 y 164 pues generan problemas
    public void parseText() {
        
        Unihan thisChar = parseChunck();
        
        this.strokesList[thisChar.getNumOfStrokes()] = thisChar.getNumOfStrokes();
        if (strokesList[thisChar.getNumOfStrokes()] == 0) {
            BSTRefGeneric<Unihan> tempBST = new BSTRefGeneric<>();
            bstStrokesArray[thisChar.getNumOfStrokes()] = tempBST;
            bstStrokesArray[thisChar.getNumOfStrokes()].insert(thisChar);
        } else {
            if (bstStrokesArray[thisChar.getNumOfStrokes()] == null) {
                BSTRefGeneric<Unihan> tempBST = new BSTRefGeneric<>();
                bstStrokesArray[thisChar.getNumOfStrokes()] = tempBST;
            }
            bstStrokesArray[thisChar.getNumOfStrokes()].insert(thisChar);
        }
        // Maneja los casos en los que hay comillas en el radical
        try {
            intRadix = (int) Math.floor(parseFloat(thisChar.getRadix()));
        } catch(NumberFormatException e) {
            String[] s = thisChar.getRadix().split("[.]", 0);
            if (s[0].charAt(s[0].length()-1) == '\'')
                s[0] = s[0].substring(0, s[0].length()-1);
            try {
                intRadix = Integer.parseInt(s[0]);
            }
            catch (NumberFormatException ex) {
                intRadix = 0;
            }
        }
        this.radixesList[intRadix] = intRadix;
        if (radixesList[intRadix] == 0) {
            tempBST = new BSTRefGeneric<>();
            bstRadixesArray[intRadix] = tempBST;
        } else {
            if (bstRadixesArray[intRadix] == null) {
                BSTRefGeneric<Unihan> tempBST = new BSTRefGeneric<>();
                bstRadixesArray[intRadix] = tempBST;
            }
            bstRadixesArray[intRadix].insert(thisChar);
        } 
        tempAVL.setRoot(tempAVL.insert(tempAVL.getRoot(), thisChar));
        for (int i = 0; i < thisChar.getSpanishDefinitions().length; i++) {
            String def = thisChar.getSpanishDefinitions()[i];
            if (def.compareTo("NONE") == 0 || def.compareTo("NINGUNA") == 0) continue;
            SpanishDef sd = new SpanishDef(thisChar.getCharacter(), def);
            esDefArray.add(sd);
        }
        while (readLine()!= null) {
            Unihan currentChar = parseChunck();
            try {
                this.strokesList[currentChar.getNumOfStrokes()] = currentChar.getNumOfStrokes();
            } catch(ArrayIndexOutOfBoundsException e) {
                break;
            }    
            if (strokesList[currentChar.getNumOfStrokes()] == 0) {
                tempBST = new BSTRefGeneric<>();
                bstStrokesArray[currentChar.getNumOfStrokes()] = tempBST;
                bstStrokesArray[currentChar.getNumOfStrokes()].insert(currentChar);
            } else {
                if (bstStrokesArray[currentChar.getNumOfStrokes()] == null) {
                    BSTRefGeneric<Unihan> tempBST = new BSTRefGeneric<>();
                    bstStrokesArray[currentChar.getNumOfStrokes()] = tempBST;
                }
                bstStrokesArray[currentChar.getNumOfStrokes()].insert(currentChar);
            }
            // Maneja los casos en los que hay comillas en el radical
            try {
                intRadix = (int) Math.floor(parseFloat(currentChar.getRadix()));
            } catch(NumberFormatException e) {
                String[] s = currentChar.getRadix().split("[.]", 0);
                if (s[0].charAt(s[0].length()-1) == '\'')
                    s[0] = s[0].substring(0, s[0].length()-1);
                intRadix = Integer.parseInt(s[0]);
            }
            this.radixesList[intRadix] = intRadix;
            if (radixesList[intRadix] == 0) {
                tempBST = new BSTRefGeneric<>();
                bstRadixesArray[intRadix] = tempBST;
            } else {
                if (bstRadixesArray[intRadix] == null) {
                    BSTRefGeneric<Unihan> tempBST = new BSTRefGeneric<>();
                    bstRadixesArray[intRadix] = tempBST;
                }
                bstRadixesArray[intRadix].insert(currentChar);
            } 
            tempAVL.setRoot(tempAVL.insert(tempAVL.getRoot(), currentChar));
            for (int i = 0; i < currentChar.getSpanishDefinitions().length; i++) {
                String def = currentChar.getSpanishDefinitions()[i];
                if (def.compareTo("NONE") == 0 || def.compareTo("NINGUNA") == 0) continue;
                SpanishDef sd = new SpanishDef(currentChar.getCharacter(), def);
                esDefArray.add(sd);
            }
            
        }
    }

    /**
     * Searches a given char
     * @param c: char to search for
     * @param selector: 'g' for the general part, 'f' for favorite part
     * @return
     */
    public Unihan searchByChar(char c, char selector) {
        NodeGeneric<Unihan> n = null;
        if (selector == 'g') { // g de general
            n = this.tempAVL.find(tempAVL.getRoot(), c);  
        } else if (selector == 'f') { // f de favoritos
            n = this.favAVL.find(favAVL.getRoot(), c);  
        }
        return n.getData();
    }
    
    // Retorna una cola con los caracteres del árbol BST que corresponde a número de trazos s
    public LinkedList<Unihan> filterByStrokes(int s, char selector) {
        if (selector == 'g') tempBST = this.bstStrokesArray[s];
        else if (selector == 'f') tempBST = this.favBSTStrokesArray[s];
        if (tempBST!=null) return tempBST.inOrderToQueue(tempBST.getRoot());
        else return null;
    }
    
    // Retorna una cola con los caracteres del árbol BST que corresponde al radical r
    public LinkedList<Unihan> filterByRadixes(int r, char selector) {
        if (selector == 'g') tempBST = this.bstRadixesArray[r];
        else if (selector == 'f') tempBST = this.favBSTRadixesArray[r];
        if (tempBST!= null) return tempBST.inOrderToQueue(tempBST.getRoot());
        else return null;
    }
    
    // No se probó este método, creo que hay que hacerlo desde la misma app
    // Solo se debe verificar si si se agrega al AVL cuando se haga clic en agregar favorito
    // Todo lo demás debe funcionar correctamente porque es prácticamente igual a parseText
    // Y todo el parseText se verificó que funciona correctamente
    public void addFavorite(Unihan u) {
        favAVL.setRoot(favAVL.insert(favAVL.getRoot(), u));
        this.favStrokesList[u.getNumOfStrokes()] = u.getNumOfStrokes();
        if (favBSTStrokesArray[u.getNumOfStrokes()] == null) {
            BSTRefGeneric<Unihan> tempBST = new BSTRefGeneric<>();
            favBSTStrokesArray[u.getNumOfStrokes()] = tempBST;
        } else {
            if (favBSTStrokesArray[u.getNumOfStrokes()] == null) {
                BSTRefGeneric<Unihan> tempBST = new BSTRefGeneric<>();
                favBSTStrokesArray[u.getNumOfStrokes()] = tempBST;
            }
            favBSTStrokesArray[u.getNumOfStrokes()].insert(u);
        }
        intRadix = (int) Math.floor(parseFloat(u.getRadix()));
        this.favRadixesList[intRadix] = intRadix;
        if (favRadixesList[intRadix] == 0) {
            tempBST = new BSTRefGeneric<>();
            favBSTRadixesArray[intRadix] = tempBST;
        } else {
            if (favBSTRadixesArray[intRadix] == null) {
                BSTRefGeneric<Unihan> tempBST = new BSTRefGeneric<>();
                favBSTRadixesArray[intRadix] = tempBST;
            }
            bstRadixesArray[intRadix].insert(u);
        } 
    }
    
    // No se probó este método, creo que hay que hacerlo desde la misma app
    // Se debe verificar si al oprimir el botón eliminar favorito en la app, se elimina 
    // del arbol AVL de favoritos
    public void deleteFavorite(Unihan u) {
        favAVL.deleteNode(favAVL.getRoot(), u);
    }
    
    
    public MaxHeap searchPattern(String p) {
        MaxHeap heap = new MaxHeap();
        this.rabinKarp = new RabinKarp();
        for (int i = 0; i < esDefArray.size(); i++) {
            SpanishDef def = esDefArray.get(i);
            if (p.length() <= def.getLen()) {
                rabinKarp.search(def.getText(), p);
                // El RabinKarp se detiene la primera vez que haya encontrado el patrón
                // Y activa el flag Found que nos dice que el caracter debe agregarse al Heap
                if (rabinKarp.found) {
                    Unihan u = tempAVL.find(tempAVL.getRoot(), def.getChar()).getData();
                    u.setScore((double)p.length()/def.getText().length());
                    // Sentencia solo para verificar los puntajes obtenidos. Puede eliminarse 
                    System.out.println(u + " " + String.format("%.5f", u.getScore()));
                    heap.insert(u);
                }
            } else continue; // Si el patrón es mayor al texto en longitud no se ejecuta el rabinkarp
        }
        return heap;
    }

    /**
     * This method shows in the command line the AVL tree
     */
    public void print() {
        System.out.println("Final Root: " + tempAVL.getRoot().getData());
        tempAVL.inOrder(tempAVL.getRoot());
    }

    /**
     * Convers a String line into a String[] array separating by ", "
     * If there are apostrophes, then it removes them 
     * @param strToParse String to parse
     * @return String[] array correctly parsed
     */
    private String[] stringToArray(String strToParse) {
        strToParse = strToParse.substring(1,strToParse.length()-1);
        String[] data = strToParse.split(", ");
        for (int i=0; i<data.length; i++) {
            data[i] = data[i].replaceAll("'", "");
        }
        return data;
    }
}